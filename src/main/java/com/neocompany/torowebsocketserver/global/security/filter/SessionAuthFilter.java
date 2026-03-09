package com.neocompany.torowebsocketserver.global.security.filter;

import com.neocompany.torowebsocketserver.global.security.auth.SessionPrincipal;
import com.neocompany.torowebsocketserver.global.security.service.SessionAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * HTTP 요청의 SID 쿠키를 검증해 SecurityContext 에 Principal 등록
 * WebSocket Handshake 와 동일한 세션 검증 로직을 재사용
 */
@Component
@RequiredArgsConstructor
public class SessionAuthFilter extends OncePerRequestFilter {

    private final SessionAuthService sessionAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String sid = extractSid(request);

        if (sid != null) {
            sessionAuthService.authenticate(sid).ifPresent(principal ->
                    SecurityContextHolder.getContext().setAuthentication(
                            toAuthentication(principal)
                    )
            );
        }

        filterChain.doFilter(request, response);
    }

    private String extractSid(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        return Arrays.stream(cookies)
                .filter(c -> "SID".equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    private UsernamePasswordAuthenticationToken toAuthentication(SessionPrincipal principal) {
        List<SimpleGrantedAuthority> authorities = Arrays.stream(principal.getRoles().split(","))
                .map(String::trim)
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }
}
