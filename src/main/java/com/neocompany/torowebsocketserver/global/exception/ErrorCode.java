package com.neocompany.torowebsocketserver.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Common
    INVALID_REQUEST(400, HttpStatus.BAD_REQUEST, "요청 형식이 다릅니다."),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN(403, HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NOT_FOUND(404, HttpStatus.NOT_FOUND, "존재하지 않는 엔드포인트 입니다."),
    INTERNAL_ERROR(502, HttpStatus.INTERNAL_SERVER_ERROR, "서버가 혼잡 하오니 잠시후 다시 시도해주세요..."),

    // Auth - 업무 실패 (HTTP 200, statusCode 201)
    EMAIL_ALREADY_EXISTS(201, HttpStatus.OK, "이미 사용중인 이메일입니다."),
    EMAIL_NOT_VERIFIED(201, HttpStatus.OK, "이메일 인증이 필요합니다."),
    INVALID_CREDENTIALS(201, HttpStatus.OK, "이메일 또는 비밀번호가 올바르지 않습니다."),
    INVALID_PASSWORD(201, HttpStatus.OK, "비밀번호가 일치하지 않습니다."),
    SOCIAL_LOGIN_CONFLICT(201, HttpStatus.OK, "이미 가입된 이메일입니다. 일반 로그인 방식을 사용하세요."),

    // User
    USER_NOT_FOUND(201, HttpStatus.OK, "존재하지 않는 사용자입니다."),

    // Room
    ROOM_NOT_FOUND(201, HttpStatus.OK, "존재하지 않는 상담방입니다."),
    ROOM_ACCESS_DENIED(201, HttpStatus.OK, "상담방 접근 권한이 없습니다."),

    // Message
    MESSAGE_INVALID(201, HttpStatus.OK, "메시지가 올바르지 않습니다."),

    // WaitingRoom
    WAITING_DUPLICATED(201, HttpStatus.OK, "이미 대기열에 등록되어 있습니다."),

    // Point
    POINT_INSUFFICIENT(201, HttpStatus.OK, "포인트가 부족합니다."),
    POINT_ORDER_NOT_FOUND(201, HttpStatus.OK, "존재하지 않는 충전 주문입니다."),
    POINT_ORDER_ALREADY_PAID(201, HttpStatus.OK, "이미 처리된 충전 주문입니다."),

    // Payment
    PAYMENT_NOT_FOUND(201, HttpStatus.OK, "존재하지 않는 결제 내역입니다."),
    PAYMENT_AMOUNT_MISMATCH(201, HttpStatus.OK, "결제 금액이 일치하지 않습니다."),
    TOSS_API_HTTP_ERROR(502, HttpStatus.BAD_GATEWAY, "결제 서버 오류."),
    TOSS_API_COMMUNICATION_ERROR(502, HttpStatus.BAD_GATEWAY, "결제 서버 통신 실패."),
    TOSS_API_RESPONSE_PARSE_ERROR(502, HttpStatus.BAD_GATEWAY, "결제 응답 파싱 실패.");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
