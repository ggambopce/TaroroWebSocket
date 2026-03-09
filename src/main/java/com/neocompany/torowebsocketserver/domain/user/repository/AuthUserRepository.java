package com.neocompany.torowebsocketserver.domain.user.repository;

import com.neocompany.torowebsocketserver.domain.user.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByUserIdAndDeletedFalse(Long userId);
}
