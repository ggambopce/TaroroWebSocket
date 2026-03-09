package com.neocompany.torowebsocketserver.domain.message.controller;

import com.neocompany.torowebsocketserver.domain.message.dto.event.ChatMessageEvent;
import com.neocompany.torowebsocketserver.domain.message.dto.event.ReadUpdatedEvent;
import com.neocompany.torowebsocketserver.domain.message.dto.event.TypingEvent;
import com.neocompany.torowebsocketserver.domain.message.dto.request.ReadMessageRequest;
import com.neocompany.torowebsocketserver.domain.message.dto.request.SendMessageRequest;
import com.neocompany.torowebsocketserver.domain.message.dto.request.TypingRequest;
import com.neocompany.torowebsocketserver.domain.message.service.MessageCommandService;
import com.neocompany.torowebsocketserver.domain.message.service.ReadReceiptService;
import com.neocompany.torowebsocketserver.domain.message.service.TypingEventService;
import com.neocompany.torowebsocketserver.global.security.auth.SessionPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * STOMP 메시지 도메인 명령 처리
 *
 * 구독:  /topic/room.{roomId}
 * 명령:  /app/rooms/{roomId}/messages   — 메시지 전송
 *        /app/rooms/{roomId}/read       — 읽음 처리
 *        /app/rooms/{roomId}/typing     — 타이핑 상태
 */
@Controller
@RequiredArgsConstructor
public class MessageStompController {

    private final MessageCommandService messageCommandService;
    private final ReadReceiptService readReceiptService;
    private final TypingEventService typingEventService;

    /** 메시지 전송 → 저장 후 방 전체 브로드캐스트 */
    @MessageMapping("/rooms/{roomId}/messages")
    @SendTo("/topic/room.{roomId}")
    public ChatMessageEvent sendMessage(@DestinationVariable Long roomId,
                                        @Payload SendMessageRequest request,
                                        Principal principal) {
        return messageCommandService.send(roomId, userId(principal), request);
    }

    /** 읽음 처리 → 방 전체에 읽음 상태 동기화 */
    @MessageMapping("/rooms/{roomId}/read")
    @SendTo("/topic/room.{roomId}")
    public ReadUpdatedEvent readMessage(@DestinationVariable Long roomId,
                                        @Payload ReadMessageRequest request,
                                        Principal principal) {
        return readReceiptService.updateReadReceipt(roomId, userId(principal), request.getLastReadMessageId());
    }

    /** 타이핑 상태 → 상대방에게 실시간 반영 */
    @MessageMapping("/rooms/{roomId}/typing")
    @SendTo("/topic/room.{roomId}")
    public TypingEvent typing(@DestinationVariable Long roomId,
                              @Payload TypingRequest request,
                              Principal principal) {
        return typingEventService.typing(roomId, userId(principal), request.isTyping());
    }

    private Long userId(Principal principal) {
        return ((SessionPrincipal) principal).getUserId();
    }
}
