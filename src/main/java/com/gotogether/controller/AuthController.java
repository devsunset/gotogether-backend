package com.gotogether.controller;

import com.gotogether.service.AuthService;
import com.gotogether.service.UserRefreshTokenService;
import com.gotogether.payload.request.LogOutRequest;
import com.gotogether.payload.request.LoginRequest;
import com.gotogether.payload.request.SignupRequest;
import com.gotogether.payload.request.TokenRefreshRequest;
import com.gotogether.payload.response.MessageResponse;
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
    return authService.authenticaeUser(loginRequest);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)  throws Exception {
    ResponseEntity<MessageResponse> body = authService.registerUser(signUpRequest);
    if (body != null) return body;
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) throws Exception {
    return authService.refreshtoken(request);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) throws Exception {
    userRefreshTokenService.deleteByUsername(logOutRequest.getUsername());
    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }

  @GetMapping("/signup/activeuser")
  public ResponseEntity<?> activeUser(@RequestParam("token") String token) throws Exception {
    authService.activeUser(token);
    return ResponseEntity.ok(new MessageResponse("active successful!"));
  }

}
