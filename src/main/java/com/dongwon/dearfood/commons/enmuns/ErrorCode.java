package com.dongwon.dearfood.commons.enmuns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SUCCESS(OK, "Success"), ERROR(INTERNAL_SERVER_ERROR, "Internal Server Error"),

    NOT_EXISTS_PRODUCT_ID(BAD_REQUEST, "BAD REQUEST");

    private final HttpStatus status;
    private final String message;
}
