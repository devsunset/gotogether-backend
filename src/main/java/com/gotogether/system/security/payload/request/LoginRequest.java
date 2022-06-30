package com.gotogether.system.security.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
	@NotBlank
	private String userid;

	@NotBlank
	private String password;

}
