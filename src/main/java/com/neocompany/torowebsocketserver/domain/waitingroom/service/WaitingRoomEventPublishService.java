package com.neocompany.torowebsocketserver.domain.waitingroom.service;

import com.neocompany.torowebsocketserver.domain.waitingroom.dto.event.WaitingRoomEvent;
import com.neocompany.torowebsocketserver.infra.messaging.StompDestination;
import com.neocompany.torowebsocketserver.infra.messaging.StompEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitingRoomEventPublishService {

    private final StompEventPublisher publisher;

    /**
     * 마스터의 대기열 변경 이벤트 → /topic/waiting.{masterId}
     * (마스터가 구독해서 대기 현황 실시간 확인)
     */
    public void publishToMaster(Long masterId, WaitingRoomEvent event) {
        publisher.broadcast(StompDestination.waiting(masterId), event);
    }

    /**
     * 대기 중인 유저에게 개인 알림 → /user/{userId}/queue/notifications
     * (유저가 호출되거나 취소됐을 때)
     */
    public void publishToUser(Long userId, WaitingRoomEvent event) {
        publisher.sendToUser(String.valueOf(userId), StompDestination.USER_NOTIFICATIONS, event);
    }
}
