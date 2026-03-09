package com.neocompany.torowebsocketserver.domain.room.controller;

import com.neocompany.torowebsocketserver.domain.room.dto.RoomResponse;
import com.neocompany.torowebsocketserver.domain.room.service.RoomService;
import com.neocompany.torowebsocketserver.global.response.ApiResponse;
import com.neocompany.torowebsocketserver.global.security.auth.SessionPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomHttpController {

    private final RoomService roomService;

    /** 방 상세 조회 — 참여자만 가능 */
    @GetMapping("/{roomId}")
    public ApiResponse<RoomResponse> getRoom(@PathVariable Long roomId,
                                             @AuthenticationPrincipal SessionPrincipal principal) {
        return ApiResponse.ok("상담방 조회 성공", roomService.getRoom(roomId, principal.getUserId()));
    }

    /** 내 방 목록 조회 */
    @GetMapping
    public ApiResponse<List<RoomResponse>> getMyRooms(@AuthenticationPrincipal SessionPrincipal principal) {
        return ApiResponse.ok("상담방 목록 조회 성공", roomService.getMyRooms(principal.getUserId()));
    }
}
