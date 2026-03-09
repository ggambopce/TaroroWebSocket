package com.neocompany.torowebsocketserver.infra.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * STOMP 이벤트 발행 핵심 인프라
 *
 * SimpMessagingTemplate 를 직접 사용하는 유일한 클래스.
 * 도메인 Publisher 들은 이 클래스를 통해서만 발행한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StompEventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 특정 topic 으로 브로드캐스트
     * 예: /topic/room.1, /topic/waiting.42
     */
    public void broadcast(String destination, Object payload) {
        log.debug("[STOMP] broadcast → {}", destination);
        messagingTemplate.convertAndSend(destination, payload);
    }

    /**
     * 특정 유저의 개인 큐로 전송
     * 예: sendToUser("42", "/queue/notifications", event)
     *     → /user/42/queue/notifications
     */
    public void sendToUser(String userId, String userQueue, Object payload) {
        log.debug("[STOMP] sendToUser → userId={}, queue={}", userId, userQueue);
        messagingTemplate.convertAndSendToUser(userId, userQueue, payload);
    }
}
