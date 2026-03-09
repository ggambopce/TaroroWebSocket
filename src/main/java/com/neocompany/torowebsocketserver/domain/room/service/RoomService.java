package com.neocompany.torowebsocketserver.domain.room.service;

import com.neocompany.torowebsocketserver.domain.room.dto.RoomResponse;
import com.neocompany.torowebsocketserver.domain.room.entity.Room;
import com.neocompany.torowebsocketserver.domain.room.repository.RoomRepository;
import com.neocompany.torowebsocketserver.global.exception.BusinessException;
import com.neocompany.torowebsocketserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomResponse getRoom(Long roomId, Long requesterId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_NOT_FOUND));

        if (!isParticipant(room, requesterId)) {
            throw new BusinessException(ErrorCode.ROOM_ACCESS_DENIED);
        }

        return new RoomResponse(room);
    }

    public List<RoomResponse> getMyRooms(Long userId) {
        return roomRepository.findMyRooms(userId).stream()
                .map(RoomResponse::new)
                .toList();
    }

    private boolean isParticipant(Room room, Long userId) {
        return userId.equals(room.getUserId()) || userId.equals(room.getMasterId());
    }
}
