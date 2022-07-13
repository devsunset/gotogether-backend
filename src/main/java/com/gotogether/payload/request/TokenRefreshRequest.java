package com.gotogether.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class TokenRefreshRequest {
  @NotBlank
  private String refreshToken;
}
