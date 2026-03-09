package com.neocompany.torowebsocketserver.domain.message.repository;

import com.neocompany.torowebsocketserver.domain.message.entity.MessageRead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageReadRepository extends JpaRepository<MessageRead, Long> {

    Optional<MessageRead> findByRoomIdAndUserId(Long roomId, Long userId);
}
