package com.gotogether.dto;

import com.gotogether.system.constants.Constants;
import com.gotogether.system.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Schema(description = "Rest 공통 응답")
@Getter
@ToString
@Builder
public class CommonResponse {
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_MIS_FORMAT));
    private final String result;
    private final int status;
    private final String error;
    private final String code;
    private final String message;
    private final String description;
    private final Object data;

    public static ResponseEntity<CommonResponse> toResponseEntity() {
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
                        .data("")
                        .build()
                );
    }

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