package com.neocompany.torowebsocketserver.domain.waitingroom.dto;

import com.neocompany.torowebsocketserver.domain.waitingroom.entity.WaitingRoom;
import com.neocompany.torowebsocketserver.domain.waitingroom.entity.WaitingStatus;
import lombok.Getter;

import java.time.Instant;

@Getter
public class WaitingRoomResponse {

    private final Long id;
    private final Long userId;
    private final Long masterId;
    private final Integer position;
    private final WaitingStatus status;
    private final Instant createdAt;

    public WaitingRoomResponse(WaitingRoom waitingRoom) {
        this.id = waitingRoom.getId();
        this.userId = waitingRoom.getUserId();
        this.masterId = waitingRoom.getMasterId();
        this.position = waitingRoom.getPosition();
        this.status = waitingRoom.getStatus();
        this.createdAt = waitingRoom.getCreatedAt();
    }
}
