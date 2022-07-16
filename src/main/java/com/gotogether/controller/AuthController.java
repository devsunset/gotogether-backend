package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.service.AuthService;
import com.gotogether.service.UserRefreshTokenService;
import com.gotogether.dto.request.LogOutRequest;
import com.gotogether.dto.request.LogInRequest;
import com.gotogether.dto.request.SignUpRequest;
import com.gotogether.dto.request.TokenRefreshRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LogInRequest loginRequest)  throws Exception {
    return CommonResponse.toResponseEntity(authService.authenticaeUser(loginRequest));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)  throws Exception {
    authService.registerUser(signUpRequest);
    return CommonResponse.toResponseEntity("","User registered successfully");
  }

  @PostMapping("/refreshtoken")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) throws Exception {
    return CommonResponse.toResponseEntity(authService.refreshtoken(request));
  }

  @PostMapping("/logout")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
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
