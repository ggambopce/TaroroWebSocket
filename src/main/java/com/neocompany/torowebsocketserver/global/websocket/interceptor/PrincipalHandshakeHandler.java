package com.neocompany.torowebsocketserver.global.websocket.interceptor;

import com.neocompany.torowebsocketserver.global.security.auth.SessionPrincipal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Component
public class PrincipalHandshakeHandler extends DefaultHandshakeHandler {

    /**
     * HandshakeInterceptor 에서 attributes 에 저장한 Principal 을 STOMP 세션에 등록
     * STOMP Controller 에서 @Header Principal 또는 SimpMessageHeaderAccessor 로 접근 가능
     */
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        return (SessionPrincipal) attributes.get("principal");
    }
}
