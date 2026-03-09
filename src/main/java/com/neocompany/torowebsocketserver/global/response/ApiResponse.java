package com.neocompany.torowebsocketserver.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final boolean success;
    private final String message;
    private final int statusCode;
    private final T data;

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, 200, data);
    }

    public static ApiResponse<Void> ok(String message) {
        return new ApiResponse<>(true, message, 200, null);
    }

    public static <T> ApiResponse<T> failure(int statusCode, String message) {
        return new ApiResponse<>(false, message, statusCode, null);
    }
}
