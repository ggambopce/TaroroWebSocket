package com.neocompany.torowebsocketserver.domain.waitingroom.entity;

import com.neocompany.torowebsocketserver.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * taroro API 서버의 waiting_room 테이블 공유 매핑
 * ※ 실제 테이블 스키마가 다를 경우 필드명/컬럼명 수정 필요
 */
@Entity
@Table(name = "waiting_room")
@Getter
public class WaitingRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "master_id", nullable = false)
    private Long masterId;

    @Column(name = "position")
    private Integer position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private WaitingStatus status;
}
