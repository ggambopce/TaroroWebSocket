package com.neocompany.torowebsocketserver.domain.room.repository;

import com.neocompany.torowebsocketserver.domain.room.entity.RoomParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomParticipantRepository extends JpaRepository<RoomParticipant, Long> {

    /** 현재 온라인 상태인 참여 기록 (leftAt = null) */
    Optional<RoomParticipant> findByRoomIdAndUserIdAndLeftAtIsNull(Long roomId, Long userId);
}
