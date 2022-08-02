package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Schema(description = "Login 요청")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LogInRequest {

    @Schema(description = "userid")
    @NotBlank
    private String username;

    @Schema(description = "password")
    @NotBlank
    private String password;

}
