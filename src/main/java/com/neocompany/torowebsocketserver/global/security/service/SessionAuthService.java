package com.neocompany.torowebsocketserver.global.security.service;

import com.neocompany.torowebsocketserver.domain.session.repository.UserSessionRepository;
import com.neocompany.torowebsocketserver.domain.user.repository.AuthUserRepository;
import com.neocompany.torowebsocketserver.global.security.auth.SessionPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionAuthService {

    private final UserSessionRepository sessionRepository;
    private final AuthUserRepository userRepository;

    /**
     * SID 쿠키로 세션 검증 후 SessionPrincipal 반환
     * - 세션 없음 → empty
     * - 세션 만료 → empty
     * - 탈퇴 유저 → empty
     * - 정상 → SessionPrincipal
     */
    public Optional<SessionPrincipal> authenticate(String sid) {
        if (sid == null || sid.isBlank()) return Optional.empty();

        return sessionRepository.findBySessionId(sid)
                .filter(session -> {
                    if (session.getExpiresAt().isBefore(Instant.now())) {
                        log.debug("[SessionAuth] 만료된 세션 sid={}", sid);
                        return false;
                    }
                    return true;
                })
                .flatMap(session -> userRepository.findByUserIdAndDeletedFalse(session.getUserId()))
                .map(user -> new SessionPrincipal(user.getUserId(), user.getEmail(), user.getRoles()));
    }
}
