package com.dongwon.dearfood.commons.exception;

import com.dongwon.dearfood.commons.enmuns.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;

import static com.dongwon.dearfood.commons.enmuns.ErrorCode.ALREADY_EXIST_STATUS;
import static com.dongwon.dearfood.commons.enmuns.ErrorCode.NOT_EXISTS_PRODUCT_ID;
import static org.springframework.http.HttpStatus.*;

@Getter
public class CustomRequestException {

    @ApiModelProperty(value = "Http status code", example = "BAD_REQUEST")
    private HttpStatus status;

    @ApiModelProperty(value = "Error Message", example = "Invalid JSON format")
    private String errorMessage;

    private ErrorCode errorCode;


    @ApiModelProperty(dataType = "java.util.Date", example = "2021-07-29 10:00")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime timeStamp;

    private CustomRequestException() {
        this.timeStamp = LocalDateTime.now();
    }

    public CustomRequestException(ApiRequestException e) {
        this();
        this.status = e.getStatus();
        this.errorMessage = e.getMessage();
    }

    public CustomRequestException(SuspendAlreadyExistException e) {
        this();
        this.status = BAD_REQUEST;
        this.errorMessage = ALREADY_EXIST_STATUS.getMessage();
        this.errorCode = ALREADY_EXIST_STATUS;

    }

    public CustomRequestException(RuntimeException e) {
        this();
        this.status = BAD_REQUEST;
        this.errorMessage = e.getMessage();

    }

    public CustomRequestException(MethodArgumentNotValidException e) {
        this();
        this.status = BAD_REQUEST;
        this.errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }

    public CustomRequestException(Exception e) {
        this();
        this.status = INTERNAL_SERVER_ERROR;
        this.errorMessage = e.getMessage();
    }


}
