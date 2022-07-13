package com.gotogether.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
	private String token;

	private String type = "Bearer";
	private String refreshToken;
	private String username;
	private String nickname;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, String refreshToken, String username, String nickname, String email, List<String> roles) {
		this.token = accessToken;
		this.refreshToken = refreshToken;
		this.username = username;
		this.nickname = nickname;
		this.email = email;
		this.roles = roles;
	}
}
