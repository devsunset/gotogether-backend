package com.gotogether.dto;

import com.gotogether.system.constants.Constants;
import com.gotogether.system.enums.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class CommonResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String result;
    private final int status;
    private final String error;
    private final String code;
    private final String message;
    private final String description;
    private final Object data;

    public static ResponseEntity<CommonResponse> toResponseEntity(Object obj) {
        ErrorCode errorCode = ErrorCode.OK;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(CommonResponse.builder()
                        .result(Constants.Result.SUCCESS)
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDescription())
                        .description(errorCode.getDescription())
                        .data(obj)
                        .build()
                );
    }

    public static ResponseEntity<CommonResponse> toResponseEntity(Object obj, String message) {
        ErrorCode errorCode = ErrorCode.OK;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(CommonResponse.builder()
                        .result(Constants.Result.SUCCESS)
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(message)
                        .description(errorCode.getDescription())
                        .data(obj)
                        .build()
                );
    }
}