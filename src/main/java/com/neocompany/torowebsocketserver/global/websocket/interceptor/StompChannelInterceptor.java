package com.neocompany.torowebsocketserver.global.websocket.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * STOMP 프레임 레벨 권한 검사
 *
 * CONNECT  - Principal 존재 확인
 * SUBSCRIBE - 허용된 destination 패턴 검사
 * SEND     - /app/ 접두사 검사
 */
@Slf4j
@Component
public class StompChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null || accessor.getCommand() == null) return message;

        switch (accessor.getCommand()) {
            case CONNECT   -> checkConnect(accessor);
            case SUBSCRIBE -> checkSubscribe(accessor);
            case SEND      -> checkSend(accessor);
            default        -> { /* ACK, NACK 등은 통과 */ }
        }
        return message;
    }

    // ── CONNECT ──────────────────────────────────────────────────────────────

    private void checkConnect(StompHeaderAccessor accessor) {
        if (accessor.getUser() == null) {
            log.warn("[STOMP] CONNECT blocked - no principal");
            throw new MessageDeliveryException("인증되지 않은 연결입니다.");
        }
        log.info("[STOMP] CONNECT - user={}", accessor.getUser().getName());
    }

    // ── SUBSCRIBE ─────────────────────────────────────────────────────────────

    private void checkSubscribe(StompHeaderAccessor accessor) {
        Principal user = requireAuth(accessor);
        String dest = accessor.getDestination();

        if (!isAllowedSubscription(dest)) {
            log.warn("[STOMP] SUBSCRIBE blocked - user={}, dest={}", user.getName(), dest);
            throw new MessageDeliveryException("구독 권한이 없습니다: " + dest);
        }
        log.info("[STOMP] SUBSCRIBE - user={}, dest={}", user.getName(), dest);
    }

    /**
     * 허용 패턴
     *   /user/queue/**        - 자신의 개인 큐 (에러 알림 포함)
     *   /topic/test           - 테스트 브로드캐스트
     *   /topic/room.{roomId}  - 방 토픽 (5단계에서 멤버십 검사 추가 예정)
     */
    private boolean isAllowedSubscription(String dest) {
        if (dest == null) return false;
        return dest.startsWith("/user/queue/")
                || dest.equals("/topic/test")
                || dest.startsWith("/topic/room.");
    }

    // ── SEND ──────────────────────────────────────────────────────────────────

    private void checkSend(StompHeaderAccessor accessor) {
        Principal user = requireAuth(accessor);
        String dest = accessor.getDestination();

        if (dest == null || !dest.startsWith("/app/")) {
            log.warn("[STOMP] SEND blocked - user={}, dest={}", user.getName(), dest);
            throw new MessageDeliveryException("허용되지 않은 destination: " + dest);
        }
    }

    // ── 공통 ──────────────────────────────────────────────────────────────────

    private Principal requireAuth(StompHeaderAccessor accessor) {
        Principal user = accessor.getUser();
        if (user == null) {
            throw new MessageDeliveryException("인증이 필요합니다.");
        }
        return user;
    }
}
