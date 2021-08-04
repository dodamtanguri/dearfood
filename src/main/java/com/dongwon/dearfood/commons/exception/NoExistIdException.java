package com.dongwon.dearfood.commons.exception;

import com.dongwon.dearfood.commons.enmuns.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NoExistIdException extends Exception{
    @ApiModelProperty(value = "Http status code", example = "BAD_REQUEST")
    private HttpStatus status;

    @ApiModelProperty(value = "Error Message", example = "이미 판매 중지된 상품입니다.")
    private String errorMessage;

    @ApiModelProperty(dataType = "java.util.Date", example = "2021-07-29 10:00")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timeStamp;

    public NoExistIdException() {
        this.timeStamp = LocalDateTime.now();
    }


    public NoExistIdException(ErrorCode code) {
        this();
        this.status = code.getStatus();
        this.errorMessage = code.getMessage();
    }

}
