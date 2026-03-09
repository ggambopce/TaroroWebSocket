package com.neocompany.torowebsocketserver.global.config;

import com.neocompany.torowebsocketserver.global.websocket.interceptor.PrincipalHandshakeHandler;
import com.neocompany.torowebsocketserver.global.websocket.interceptor.WebSocketHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketHandshakeInterceptor handshakeInterceptor;
    private final PrincipalHandshakeHandler handshakeHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 운영용 - SockJS fallback 포함 (프론트 연결)
        registry.addEndpoint("/ws/chat")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(handshakeHandler)
                .addInterceptors(handshakeInterceptor)
                .withSockJS();

        // 테스트용 - 순수 WebSocket (stomp-test.html)
        registry.addEndpoint("/ws/chat-raw")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(handshakeHandler)
                .addInterceptors(handshakeInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }
}
