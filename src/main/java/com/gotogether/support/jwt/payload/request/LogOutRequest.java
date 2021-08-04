package com.gotogether.support.jwt.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogOutRequest {
  private Long userId;
}
