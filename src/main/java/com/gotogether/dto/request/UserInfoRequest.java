package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema(description = "UserInfo 요청")
@Getter
@Setter
@ToString
public class UserInfoRequest {

    @Size(max = 100)
    private String introduce;

    @Size(max = 500)
    private String note;

    @Column(length = 250)
    private String github;

    @Column(length = 250)
    private String homepage;

    private String skill;
}
