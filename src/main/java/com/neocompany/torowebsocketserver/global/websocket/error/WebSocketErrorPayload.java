package com.neocompany.torowebsocketserver.global.websocket.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebSocketErrorPayload {
    private final int code;
    private final String message;
}
