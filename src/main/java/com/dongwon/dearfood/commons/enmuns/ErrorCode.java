package com.dongwon.dearfood.commons.enmuns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SUCCESS(OK, "Success"),
    ERROR(INTERNAL_SERVER_ERROR, "Internal Server Error"),
    NO_REQUEST_PARAMETER(BAD_REQUEST,"요청 파라미터를 확인해주세요"),
    NO_EXIST_ID(BAD_REQUEST, "존재하지 않는 아이디 입니다."),
    ALREADY_SUSPEND_STATUS(BAD_REQUEST, "이미 판매중지 된 상품입니다.");

    private final HttpStatus status;
    private final String message;
}
