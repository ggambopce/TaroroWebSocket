package com.neocompany.torowebsocketserver.global.websocket.interceptor;

import com.neocompany.torowebsocketserver.global.security.auth.SessionPrincipal;
import com.neocompany.torowebsocketserver.global.security.service.SessionAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private final SessionAuthService sessionAuthService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {

        String sid = extractSid(request);
        Optional<SessionPrincipal> principal = sessionAuthService.authenticate(sid);

        if (principal.isEmpty()) {
            log.warn("[WS Handshake] 인증 실패 - SID={}", sid);
            return false;
        }

        attributes.put("principal", principal.get());
        log.info("[WS Handshake] 인증 성공 - userId={}, email={}",
                principal.get().getUserId(), principal.get().getEmail());
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {}

    private String extractSid(ServerHttpRequest request) {
        if (!(request instanceof ServletServerHttpRequest servletRequest)) return null;

        HttpServletRequest httpRequest = servletRequest.getServletRequest();
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if ("SID".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
