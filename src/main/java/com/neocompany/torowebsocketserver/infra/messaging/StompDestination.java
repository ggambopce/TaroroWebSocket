package com.neocompany.torowebsocketserver.infra.messaging;

/**
 * STOMP destination 문자열 상수 모음
 *
 * 하드코딩 대신 이 클래스를 통해 모든 destination 을 관리한다.
 *
 * topic (브로드캐스트)
 *   /topic/room.{roomId}        - 방 이벤트
 *   /topic/waiting.{masterId}   - 마스터 대기열 이벤트
 *
 * queue (유저 개인)
 *   /user/{userId}/queue/notifications  - 개인 알림
 *   /user/{userId}/queue/signaling      - WebRTC 시그널링
 *   /user/{userId}/queue/errors         - 에러 알림
 */
public final class StompDestination {

    private StompDestination() {}

    // ── Topic (브로드캐스트) ───────────────────────────────────────────────────

    public static String room(Long roomId) {
        return "/topic/room." + roomId;
    }

    public static String waiting(Long masterId) {
        return "/topic/waiting." + masterId;
    }

    // ── User Queue (개인) ──────────────────────────────────────────────────────

    /** /user/queue/notifications — convertAndSendToUser 시 자동 prefix 적용 */
    public static final String USER_NOTIFICATIONS = "/queue/notifications";

    /** /user/queue/signaling */
    public static final String USER_SIGNALING = "/queue/signaling";

    /** /user/queue/errors */
    public static final String USER_ERRORS = "/queue/errors";
}
