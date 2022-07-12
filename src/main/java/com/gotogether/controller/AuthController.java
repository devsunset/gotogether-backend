package com.gotogether.controller;

import com.gotogether.service.AuthService;
import com.gotogether.service.UserRefreshTokenService;
import com.gotogether.service.UserActiveTokenService;
import com.gotogether.system.security.payload.request.LogOutRequest;
import com.gotogether.system.security.payload.request.LoginRequest;
import com.gotogether.system.security.payload.request.SignupRequest;
import com.gotogether.system.security.payload.request.TokenRefreshRequest;
import com.gotogether.system.security.payload.response.MessageResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthService authService;

  @Autowired
  UserRefreshTokenService userRefreshTokenService;

  @Autowired
  UserActiveTokenService userActiveTokenService;

  @Operation(summary = "signin", description = "signin api")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
          @ApiResponse(responseCode = "404", description = "NOT FOUND"),
          @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return authService.authenticaeUser(loginRequest);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    ResponseEntity<MessageResponse> body = authService.registerUser(signUpRequest);
    if (body != null) return body;

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    return authService.refreshtoken(request);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
    userRefreshTokenService.deleteByUsername(logOutRequest.getUsername());
    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }

  @GetMapping("/signup/activeuser")
  public String activeUser(@RequestParam("token") String token) {
    authService.activeUser(token);
    return null;
  }

}
