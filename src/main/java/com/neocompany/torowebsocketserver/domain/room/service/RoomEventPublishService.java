package com.neocompany.torowebsocketserver.domain.room.service;

import com.neocompany.torowebsocketserver.domain.room.dto.event.RoomEvent;
import com.neocompany.torowebsocketserver.infra.messaging.StompDestination;
import com.neocompany.torowebsocketserver.infra.messaging.StompEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomEventPublishService {

    private final StompEventPublisher publisher;

    /** 방 이벤트 브로드캐스트 → /topic/room.{roomId} */
    public void publish(Long roomId, RoomEvent event) {
        publisher.broadcast(StompDestination.room(roomId), event);
    }
}
