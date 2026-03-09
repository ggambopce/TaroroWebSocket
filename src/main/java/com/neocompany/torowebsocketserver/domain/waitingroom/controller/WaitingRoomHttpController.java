package com.neocompany.torowebsocketserver.domain.waitingroom.controller;

import com.neocompany.torowebsocketserver.domain.waitingroom.dto.WaitingRoomResponse;
import com.neocompany.torowebsocketserver.domain.waitingroom.service.WaitingRoomService;
import com.neocompany.torowebsocketserver.global.response.ApiResponse;
import com.neocompany.torowebsocketserver.global.security.auth.SessionPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/waiting-room")
@RequiredArgsConstructor
public class WaitingRoomHttpController {

    private final WaitingRoomService waitingRoomService;

    /** 내 대기 현황 조회 */
    @GetMapping
    public ApiResponse<WaitingRoomResponse> getMyWaiting(@AuthenticationPrincipal SessionPrincipal principal) {
        return ApiResponse.ok("대기 현황 조회 성공", waitingRoomService.getMyWaiting(principal.getUserId()));
    }
}
