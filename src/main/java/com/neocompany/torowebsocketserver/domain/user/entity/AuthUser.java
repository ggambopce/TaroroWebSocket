package com.neocompany.torowebsocketserver.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * API 서버의 user 테이블을 공유 (읽기 전용)
 * 인증에 필요한 최소 필드만 매핑
 *
 * ※ 테이블명이 다르면 @Table(name = "users") 로 변경
 */
@Entity
@Table(name = "user")
@Getter
public class AuthUser {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(name = "login_type")
    private String loginType;

    @Column(nullable = false)
    private String roles;

    @Column(nullable = false)
    private boolean deleted;
}
