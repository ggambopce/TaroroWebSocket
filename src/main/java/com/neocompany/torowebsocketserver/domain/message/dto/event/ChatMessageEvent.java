package com.neocompany.torowebsocketserver.domain.message.dto.event;

import com.neocompany.torowebsocketserver.domain.message.entity.ChatMessage;
import com.neocompany.torowebsocketserver.domain.message.entity.MessageType;
import lombok.Getter;

import java.time.Instant;

/**
 * 메시지 전송 이벤트 — /topic/room.{roomId} 브로드캐스트
 */
@Getter
public class ChatMessageEvent {

    private final String eventType = "CHAT_MESSAGE";
    private final Long id;
    private final Long roomId;
    private final Long senderId;
    private final String content;
    private final MessageType messageType;
    private final Instant createdAt;

    public ChatMessageEvent(ChatMessage message) {
        this.id = message.getId();
        this.roomId = message.getRoomId();
        this.senderId = message.getSenderId();
        this.content = message.getContent();
        this.messageType = message.getMessageType();
        this.createdAt = message.getCreatedAt();
    }
}
