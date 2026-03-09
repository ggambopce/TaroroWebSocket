package com.neocompany.torowebsocketserver.domain.room.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * 방 실시간 참여 현황 추적 (WebSocket 서버 전용 테이블)
 * enter 시 생성, leave 시 left_at 업데이트
 */
@Entity
@Table(name = "room_participant",
        indexes = @Index(name = "idx_room_participant_room_user", columnList = "room_id, user_id"))
@Getter
public class RoomParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(name = "joined_at", updatable = false)
    private Instant joinedAt;

    @Column(name = "left_at")
    private Instant leftAt;

    // ── 팩토리 메서드 ─────────────────────────────────────────────────────────

    public static RoomParticipant join(Long roomId, Long userId) {
        RoomParticipant p = new RoomParticipant();
        p.roomId = roomId;
        p.userId = userId;
        return p;
    }

    // ── 도메인 메서드 ─────────────────────────────────────────────────────────

    public void leave() {
        this.leftAt = Instant.now();
    }

    public boolean isOnline() {
        return this.leftAt == null;
    }
}
