package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema(description = "UserInfo 요청")
@Getter
@Setter
@ToString
public class UserSkillRequest {

    @NotBlank
    @Size(max = 50)
    private String item;

    @NotBlank
    @Size(max = 500)
    private String itemLevel;

}
