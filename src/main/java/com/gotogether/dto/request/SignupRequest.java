package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

import javax.validation.constraints.*;
@Schema(description = "Signup 요청")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignupRequest {

    @Schema(description = "userid == username")
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @Schema(description = "nickname")
    @NotBlank
    @Size(min = 3, max = 20)
    private String nickname;

    @Schema(description = "email")
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Schema(description = "role")
    private Set<String> role;

    @Schema(description = "password")
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
