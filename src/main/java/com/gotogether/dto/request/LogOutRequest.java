package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Schema(description = "Logout 요청")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LogOutRequest {

  @Schema(description = "userid == username")
  private String username;

}
