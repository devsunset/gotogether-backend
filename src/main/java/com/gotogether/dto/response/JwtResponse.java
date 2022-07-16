package com.gotogether.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Schema(description = "Jwt 응답")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class JwtResponse {

	@Schema(description = "token")
	private String token;

	@Schema(description = "auth type")
	private String type = "Bearer";
	@Schema(description = "refreshToken")
	private String refreshToken;
	@Schema(description = "userid == username")
	private String username;
	@Schema(description = "nickname")
	private String nickname;
	@Schema(description = "email")
	private String email;
	@Schema(description = "roles")
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
