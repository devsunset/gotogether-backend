package com.gotogether.controller;

import com.gotogether.payload.CommonResponse;
import com.gotogether.service.AuthService;
import com.gotogether.service.UserRefreshTokenService;
import com.gotogether.payload.request.LogOutRequest;
import com.gotogether.payload.request.LoginRequest;
import com.gotogether.payload.request.SignupRequest;
import com.gotogether.payload.request.TokenRefreshRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthService authService;

  @Autowired
  UserRefreshTokenService userRefreshTokenService;


  @Operation(summary = "signin", description = "signin api")
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)  throws Exception {
    return CommonResponse.toResponseEntity(authService.authenticaeUser(loginRequest));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)  throws Exception {
    authService.registerUser(signUpRequest);
    return CommonResponse.toResponseEntity("","User registered successfully");
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) throws Exception {
    return CommonResponse.toResponseEntity(authService.refreshtoken(request));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) throws Exception {
    userRefreshTokenService.deleteByUsername(logOutRequest.getUsername());
    return CommonResponse.toResponseEntity("","Log out successful");
  }

  @GetMapping("/signup/activeuser")
  public ResponseEntity<?> activeUser(@RequestParam("token") String token) throws Exception {
    authService.activeUser(token);
    return CommonResponse.toResponseEntity("","active successful");
  }

}
