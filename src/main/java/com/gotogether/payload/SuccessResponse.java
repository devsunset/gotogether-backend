package com.gotogether.payload;

import com.gotogether.system.constants.Constants;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.exception.CustomException;
import com.gotogether.system.exception.TokenRefreshException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class SuccessResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String result;
    private final int status;
    private final String error;
    private final String code;
    private final String message;
    private final String description;
    private final Object data;

    public static ResponseEntity<SuccessResponse> toResponseEntity(Object obj) {
        ErrorCode errorCode = ErrorCode.OK;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(SuccessResponse.builder()
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
}