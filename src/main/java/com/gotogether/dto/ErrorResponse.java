package com.gotogether.dto;

import com.gotogether.system.constants.Constants;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.exception.CustomException;
import com.gotogether.system.exception.TokenRefreshException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@Builder
public class ErrorResponse {
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_MIS_FORMAT));
    private final String result;
    private final int status;
    private final String error;
    private final String code;
    private final String message;
    private final String description;
    private final Object data;

    public static ResponseEntity<ErrorResponse> toResponseEntity(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .result(Constants.Result.ERROR)
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDescription())
                        .description(e.getMessage())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .result(Constants.Result.ERROR)
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDescription())
                        .description(e.getMessage())
                        .data("")
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(TokenRefreshException e) {
        ErrorCode errorCode = ErrorCode.TOKEN_INVALID;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .result(Constants.Result.ERROR)
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDescription())
                        .description(e.getMessage())
                        .build()
                );
    }
}