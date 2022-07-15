package com.gotogether.system.aop;

import com.gotogether.system.exception.CustomException;
import com.gotogether.dto.ErrorResponse;
import com.gotogether.system.exception.TokenRefreshException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ErrorResponse.toResponseEntity(e);
    }

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ErrorResponse.toResponseEntity(e);
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleTokenRefreshException(TokenRefreshException e) {
        return ErrorResponse.toResponseEntity(e);
    }

}