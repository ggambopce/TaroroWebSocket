package com.neocompany.torowebsocketserver.domain.message.controller;

import com.neocompany.torowebsocketserver.domain.message.dto.ChatMessageResponse;
import com.neocompany.torowebsocketserver.domain.message.service.MessageService;
import com.neocompany.torowebsocketserver.global.response.ApiResponse;
import com.neocompany.torowebsocketserver.global.security.auth.SessionPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms/{roomId}/messages")
@RequiredArgsConstructor
public class MessageHttpController {

    private final MessageService messageService;

    /**
     * 메시지 목록 조회 (커서 기반 페이징)
     *
     * GET /api/rooms/{roomId}/messages?cursor=123&size=20
     * cursor 없으면 최신 20개, 있으면 해당 id 이전 메시지
     */
    @GetMapping
    public ApiResponse<ChatMessageResponse.PageResult> getMessages(
            @PathVariable Long roomId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal SessionPrincipal principal) {

        return ApiResponse.ok("메시지 목록 조회 성공",
                messageService.getMessages(roomId, principal.getUserId(), cursor, size));
    }
}
