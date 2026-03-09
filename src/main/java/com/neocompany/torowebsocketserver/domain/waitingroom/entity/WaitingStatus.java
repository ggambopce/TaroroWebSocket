package com.neocompany.torowebsocketserver.domain.waitingroom.entity;

public enum WaitingStatus {
    WAITING,    // 대기 중
    CALLED,     // 호출됨 (방 생성 완료)
    CANCELLED   // 취소
}
