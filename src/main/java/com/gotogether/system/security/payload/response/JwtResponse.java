package com.gotogether.system.security.payload.response;

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

	private String userid;
	private String username;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, String refreshToken, String userid, String username, String email, List<String> roles) {
		this.token = accessToken;
		this.refreshToken = refreshToken;
		this.userid = userid;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}
}
