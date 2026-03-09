package com.neocompany.torowebsocketserver.domain.waitingroom.dto.event;

import com.neocompany.torowebsocketserver.domain.waitingroom.entity.WaitingStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

/**
 * 대기열 상태 변경 이벤트 — /topic/waiting.{masterId} 브로드캐스트
 */
@Getter
@Builder
public class WaitingRoomEvent {

    private final String eventType;  // QUEUE_UPDATED, CALLED, CANCELLED
    private final Long waitingId;
    private final Long userId;
    private final Long masterId;
    private final Integer position;
    private final WaitingStatus status;
    private final Instant timestamp;

    public static WaitingRoomEvent of(WaitingRoomEventType type,
                                      Long waitingId, Long userId, Long masterId,
                                      Integer position, WaitingStatus status) {
        return WaitingRoomEvent.builder()
                .eventType(type.name())
                .waitingId(waitingId)
                .userId(userId)
                .masterId(masterId)
                .position(position)
                .status(status)
                .timestamp(Instant.now())
                .build();
    }
}
