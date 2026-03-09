package com.neocompany.torowebsocketserver.domain.waitingroom.dto.event;

public enum WaitingRoomEventType {
    QUEUE_UPDATED,  // 대기 순서 변경
    CALLED,         // 상담 호출됨
    CANCELLED       // 대기 취소
}
