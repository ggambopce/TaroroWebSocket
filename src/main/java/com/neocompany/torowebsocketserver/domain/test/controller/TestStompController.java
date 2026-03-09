package com.neocompany.torowebsocketserver.domain.test.controller;

import com.neocompany.torowebsocketserver.global.security.auth.SessionPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.Map;

@Slf4j
@Controller
public class TestStompController {

    /**
     * 테스트용 브로드캐스트
     * SEND /app/test → broadcast to /topic/test
     */
    @MessageMapping("/test")
    @SendTo("/topic/test")
    public Map<String, Object> test(Map<String, Object> payload, Principal principal) {
        SessionPrincipal sp = (SessionPrincipal) principal;
        log.info("[TestStomp] userId={}, email={}, payload={}", sp.getUserId(), sp.getEmail(), payload);

        return Map.of(
                "eventType", "TEST_ECHO",
                "createdAt", ZonedDateTime.now().toString(),
                "userId", sp.getUserId(),
                "payload", payload
        );
    }
}
