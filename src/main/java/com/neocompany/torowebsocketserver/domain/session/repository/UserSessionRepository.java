package com.neocompany.torowebsocketserver.domain.session.repository;

import com.neocompany.torowebsocketserver.domain.session.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findBySessionId(String sessionId);
}
