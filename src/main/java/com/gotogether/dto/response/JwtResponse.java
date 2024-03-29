package com.gotogether.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JwtResponse {

    private String token;

    private String type = "Bearer";
    private String refreshToken;
    @Schema(description = "userid")
    private String username;
    private String nickname;
    private String email;
    private List<String> roles;

    public JwtResponse(String token, String refreshToken, String username, String nickname, String email, List<String> roles) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.roles = roles;
    }
}
