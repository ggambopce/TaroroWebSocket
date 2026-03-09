package com.neocompany.torowebsocketserver.domain.waitingroom.service;

import com.neocompany.torowebsocketserver.domain.waitingroom.dto.WaitingRoomResponse;
import com.neocompany.torowebsocketserver.domain.waitingroom.entity.WaitingStatus;
import com.neocompany.torowebsocketserver.domain.waitingroom.repository.WaitingRoomRepository;
import com.neocompany.torowebsocketserver.global.exception.BusinessException;
import com.neocompany.torowebsocketserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WaitingRoomService {

    private final WaitingRoomRepository waitingRoomRepository;

    /** 내 대기 현황 조회 — WAITING 상태인 항목 */
    public WaitingRoomResponse getMyWaiting(Long userId) {
        return waitingRoomRepository.findByUserIdAndStatus(userId, WaitingStatus.WAITING)
                .map(WaitingRoomResponse::new)
                .orElseThrow(() -> new BusinessException(ErrorCode.WAITING_NOT_FOUND));
    }
}
