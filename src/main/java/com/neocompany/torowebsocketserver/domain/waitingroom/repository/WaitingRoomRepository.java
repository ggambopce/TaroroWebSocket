package com.neocompany.torowebsocketserver.domain.waitingroom.repository;

import com.neocompany.torowebsocketserver.domain.waitingroom.entity.WaitingRoom;
import com.neocompany.torowebsocketserver.domain.waitingroom.entity.WaitingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WaitingRoomRepository extends JpaRepository<WaitingRoom, Long> {

    /** 내 대기열 항목 (WAITING 상태) */
    Optional<WaitingRoom> findByUserIdAndStatus(Long userId, WaitingStatus status);

    /** 특정 마스터 대기열 전체 (순서대로) */
    List<WaitingRoom> findByMasterIdAndStatusOrderByPositionAsc(Long masterId, WaitingStatus status);
}
