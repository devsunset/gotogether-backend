package com.gotogether.system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),
    TOKEN_INVALID(HttpStatus.FORBIDDEN, "Refresh token was expired."),
    NEED_TO_LOGIN(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String description;

}