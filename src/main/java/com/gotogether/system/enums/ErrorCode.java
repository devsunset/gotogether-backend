package com.gotogether.system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    OK(HttpStatus.OK, "Ok."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    TOKEN_INVALID(HttpStatus.FORBIDDEN, "Refresh token was expired."),
    USER_ALREADY_USE(HttpStatus.BAD_REQUEST, "userid is already use."),
    NICKNAME_ALREADY_USE(HttpStatus.BAD_REQUEST, "nickname is already use."),
    EMAIL_ALREADY_USE(HttpStatus.BAD_REQUEST, "email is already in use."),
    NOT_EXISTS_ROLE(HttpStatus.NOT_FOUND, "Role is not found."),

    NEED_TO_LOGIN(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    NOT_EXISTS_DATA(HttpStatus.BAD_REQUEST, "해당 데이터가 존재 하지 않습니다."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "유효한 Category 값이 아닙니다."),
    NOT_CHANG_EQUAL_CATEGEORY(HttpStatus.BAD_REQUEST, "동일한 Post Category 로 변경이 안됩니다."),
    NOT_ROLE_ADMIN(HttpStatus.BAD_REQUEST, "Admin 권한이 아닙니다."),
    NOT_SEND_NOTE_SELF(HttpStatus.BAD_REQUEST, "자신에게 Note를 전송 할 수 없습니다."),
    INVALID_SKILL_LEVEL_TYPE(HttpStatus.BAD_REQUEST, "유효한 Skill Level 값이 아닙니다."),
    SKILL_ITEM_DUPLICATE(HttpStatus.BAD_REQUEST, "Skill 값이 중복 됩니다."),
    INVALID_INVOLVE_TYPE(HttpStatus.BAD_REQUEST, "유효한 참여 Type 값이 아닙니다."),
    NOT_AUTH_NOTE(HttpStatus.BAD_REQUEST, "본인의 Note가 아닙니다."),
    NOT_WRITE_POST(HttpStatus.UNAUTHORIZED, "본인이 작성 한 게시물이 아닙니다.");

    private final HttpStatus httpStatus;
    private final String description;

}