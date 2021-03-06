package com.dongwon.dearfood.commons.handler;

import com.dongwon.dearfood.commons.exception.ApiRequestException;
import com.dongwon.dearfood.commons.exception.CustomRequestException;
import com.dongwon.dearfood.commons.exception.NoExistIdException;
import com.dongwon.dearfood.commons.exception.SuspendAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<?> handleApiRequestException(final ApiRequestException e) {
        return ResponseEntity.status(e.getStatus()).body(new CustomRequestException(e));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, NoSuchElementException.class, RuntimeException.class})
    public ResponseEntity<?> handleIllegalArgumentException(final RuntimeException e) {
        return ResponseEntity.badRequest().body(new CustomRequestException(e));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(final Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new CustomRequestException(e));
    }

    @ExceptionHandler(SuspendAlreadyExistException.class)
    public ResponseEntity<?> handleException(SuspendAlreadyExistException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new CustomRequestException(e));
    }

    @ExceptionHandler(NoExistIdException.class)
    public ResponseEntity<?> handleException(NoExistIdException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new CustomRequestException(e));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(new CustomRequestException(e));
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingParams(MissingServletRequestParameterException e) {

    }

}
