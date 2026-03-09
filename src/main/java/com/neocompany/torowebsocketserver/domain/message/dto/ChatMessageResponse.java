package com.neocompany.torowebsocketserver.domain.message.dto;

import com.neocompany.torowebsocketserver.domain.message.entity.ChatMessage;
import com.neocompany.torowebsocketserver.domain.message.entity.MessageType;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class ChatMessageResponse {

    private final Long id;
    private final Long roomId;
    private final Long senderId;
    private final String content;
    private final MessageType messageType;
    private final Instant createdAt;

    public ChatMessageResponse(ChatMessage message) {
        this.id = message.getId();
        this.roomId = message.getRoomId();
        this.senderId = message.getSenderId();
        this.content = message.getContent();
        this.messageType = message.getMessageType();
        this.createdAt = message.getCreatedAt();
    }

    @Getter
    public static class PageResult {
        private final List<ChatMessageResponse> messages;
        private final boolean hasNext;
        private final Long nextCursor;

        public PageResult(List<ChatMessageResponse> messages, boolean hasNext) {
            this.messages = messages;
            this.hasNext = hasNext;
            this.nextCursor = hasNext && !messages.isEmpty()
                    ? messages.get(messages.size() - 1).getId()
                    : null;
        }
    }
}
