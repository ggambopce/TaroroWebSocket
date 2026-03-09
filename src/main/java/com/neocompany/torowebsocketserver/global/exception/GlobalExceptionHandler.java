package com.neocompany.torowebsocketserver.global.exception;

import com.neocompany.torowebsocketserver.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 업무/비즈니스 에러 → HTTP 200, body statusCode 201
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("[BusinessException] code={}, message={}", errorCode.getCode(), e.getMessage());
        return ResponseEntity.ok()
                .body(ApiResponse.failure(errorCode.getCode(), e.getMessage()));
    }

    // Validation 에러 → HTTP 200, body statusCode 201
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("[ValidationException] message={}", message);
        return ResponseEntity.ok()
                .body(ApiResponse.failure(ErrorCode.INVALID_REQUEST.getCode(), message));
    }

    // 존재하지 않는 경로 → HTTP 404, body statusCode 404
    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ApiResponse<?>> handleNoHandlerFoundException() {
        return ResponseEntity.status(404)
                .body(ApiResponse.failure(ErrorCode.NOT_FOUND.getCode(), ErrorCode.NOT_FOUND.getMessage()));
    }

    // 서버 에러 → HTTP 500, body statusCode 502
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("[UnhandledException]", e);
        return ResponseEntity.status(500)
                .body(ApiResponse.failure(ErrorCode.INTERNAL_ERROR.getCode(), ErrorCode.INTERNAL_ERROR.getMessage()));
    }
}
