package com.neocompany.torowebsocketserver.domain.message.service;

import com.neocompany.torowebsocketserver.domain.message.dto.event.ChatMessageEvent;
import com.neocompany.torowebsocketserver.domain.message.dto.event.ReadUpdatedEvent;
import com.neocompany.torowebsocketserver.domain.message.dto.event.TypingEvent;
import com.neocompany.torowebsocketserver.infra.messaging.StompDestination;
import com.neocompany.torowebsocketserver.infra.messaging.StompEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageEventPublishService {

    private final StompEventPublisher publisher;

    /** 메시지 전송 이벤트 → /topic/room.{roomId} */
    public void publishMessage(Long roomId, ChatMessageEvent event) {
        publisher.broadcast(StompDestination.room(roomId), event);
    }

    /** 읽음 처리 이벤트 → /topic/room.{roomId} */
    public void publishReadUpdated(Long roomId, ReadUpdatedEvent event) {
        publisher.broadcast(StompDestination.room(roomId), event);
    }

    /** 타이핑 이벤트 → /topic/room.{roomId} */
    public void publishTyping(Long roomId, TypingEvent event) {
        publisher.broadcast(StompDestination.room(roomId), event);
    }
}
