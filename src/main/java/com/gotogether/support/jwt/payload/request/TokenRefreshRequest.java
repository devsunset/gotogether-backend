package com.gotogether.support.jwt.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class TokenRefreshRequest {
  @NotBlank
  private String refreshToken;
}
