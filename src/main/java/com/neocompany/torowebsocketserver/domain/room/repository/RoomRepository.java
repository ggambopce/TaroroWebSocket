package com.neocompany.torowebsocketserver.domain.room.repository;

import com.neocompany.torowebsocketserver.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    /** 내가 참여한 방 목록 (유저 or 마스터) */
    @Query("SELECT r FROM Room r WHERE r.userId = :userId OR r.masterId = :userId ORDER BY r.createdAt DESC")
    List<Room> findMyRooms(@Param("userId") Long userId);
}
