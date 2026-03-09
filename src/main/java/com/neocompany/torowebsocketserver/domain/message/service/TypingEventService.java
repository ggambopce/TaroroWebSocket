package com.neocompany.torowebsocketserver.domain.message.service;

import com.neocompany.torowebsocketserver.domain.message.dto.event.TypingEvent;
import com.neocompany.torowebsocketserver.domain.room.entity.Room;
import com.neocompany.torowebsocketserver.domain.room.repository.RoomRepository;
import com.neocompany.torowebsocketserver.global.exception.BusinessException;
import com.neocompany.torowebsocketserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TypingEventService {

    private final RoomRepository roomRepository;

    /**
     * 타이핑 이벤트 — DB 저장 없이 이벤트만 생성
     */
    public TypingEvent typing(Long roomId, Long userId, boolean isTyping) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_NOT_FOUND));

        if (!room.isParticipant(userId)) {
            throw new BusinessException(ErrorCode.ROOM_ACCESS_DENIED);
        }

        return new TypingEvent(roomId, userId, isTyping);
    }
}
