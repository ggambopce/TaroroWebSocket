package com.neocompany.torowebsocketserver.domain.room.dto;

import com.neocompany.torowebsocketserver.domain.room.entity.Room;
import com.neocompany.torowebsocketserver.domain.room.entity.RoomStatus;
import lombok.Getter;

import java.time.Instant;

@Getter
public class RoomResponse {

    private final Long id;
    private final Long userId;
    private final Long masterId;
    private final RoomStatus status;
    private final Instant startedAt;
    private final Instant endedAt;
    private final Instant createdAt;

    public RoomResponse(Room room) {
        this.id = room.getId();
        this.userId = room.getUserId();
        this.masterId = room.getMasterId();
        this.status = room.getStatus();
        this.startedAt = room.getStartedAt();
        this.endedAt = room.getEndedAt();
        this.createdAt = room.getCreatedAt();
    }
}
