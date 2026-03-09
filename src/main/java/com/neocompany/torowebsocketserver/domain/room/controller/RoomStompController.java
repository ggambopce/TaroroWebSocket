package com.neocompany.torowebsocketserver.domain.room.controller;

import com.neocompany.torowebsocketserver.domain.room.dto.event.RoomEvent;
import com.neocompany.torowebsocketserver.domain.room.service.RoomCommandService;
import com.neocompany.torowebsocketserver.global.security.auth.SessionPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * STOMP 방 도메인 명령 처리
 *
 * 구독:  /topic/room.{roomId}
 * 명령:  /app/rooms/{roomId}/enter|leave|start|end
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class RoomStompController {

    private final RoomCommandService roomCommandService;

    /** 입장 — 참여자 누구나 */
    @MessageMapping("/rooms/{roomId}/enter")
    @SendTo("/topic/room.{roomId}")
    public RoomEvent enter(@DestinationVariable Long roomId, Principal principal) {
        return roomCommandService.enter(roomId, userId(principal));
    }

    /** 퇴장 — 참여자 누구나 */
    @MessageMapping("/rooms/{roomId}/leave")
    @SendTo("/topic/room.{roomId}")
    public RoomEvent leave(@DestinationVariable Long roomId, Principal principal) {
        return roomCommandService.leave(roomId, userId(principal));
    }

    /** 상담 시작 — 마스터 전용 */
    @MessageMapping("/rooms/{roomId}/start")
    @SendTo("/topic/room.{roomId}")
    public RoomEvent start(@DestinationVariable Long roomId, Principal principal) {
        return roomCommandService.start(roomId, userId(principal));
    }

    /** 상담 종료 — 마스터 전용 */
    @MessageMapping("/rooms/{roomId}/end")
    @SendTo("/topic/room.{roomId}")
    public RoomEvent end(@DestinationVariable Long roomId, Principal principal) {
        return roomCommandService.end(roomId, userId(principal));
    }

    private Long userId(Principal principal) {
        return ((SessionPrincipal) principal).getUserId();
    }
}
