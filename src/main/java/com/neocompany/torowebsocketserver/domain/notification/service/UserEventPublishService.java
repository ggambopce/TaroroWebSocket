package com.neocompany.torowebsocketserver.domain.notification.service;

import com.neocompany.torowebsocketserver.domain.notification.dto.event.UserNotificationEvent;
import com.neocompany.torowebsocketserver.infra.messaging.StompDestination;
import com.neocompany.torowebsocketserver.infra.messaging.StompEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventPublishService {

    private final StompEventPublisher publisher;

    /**
     * 특정 유저에게 개인 알림 전송
     * → /user/{userId}/queue/notifications
     */
    public void publish(Long userId, UserNotificationEvent event) {
        publisher.sendToUser(String.valueOf(userId), StompDestination.USER_NOTIFICATIONS, event);
    }
}
