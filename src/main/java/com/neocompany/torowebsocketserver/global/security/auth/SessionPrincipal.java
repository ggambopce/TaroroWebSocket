package com.neocompany.torowebsocketserver.global.security.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@Getter
@RequiredArgsConstructor
public class SessionPrincipal implements Principal {

    private final Long userId;
    private final String email;
    private final String roles;

    /**
     * STOMP Controller 에서 principal.getName() 으로 userId 조회
     */
    @Override
    public String getName() {
        return String.valueOf(userId);
    }

    public boolean isAdmin() {
        return roles != null && roles.contains("ROLE_ADMIN");
    }
}
