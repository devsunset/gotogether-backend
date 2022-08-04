package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.LogInRequest;
import com.gotogether.dto.request.LogOutRequest;
import com.gotogether.dto.request.SignUpRequest;
import com.gotogether.dto.request.TokenRefreshRequest;
import com.gotogether.service.AuthService;
import com.gotogether.service.UserRefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    UserRefreshTokenService userRefreshTokenService;

    @Operation(summary = "signin", description = "signin api")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LogInRequest loginRequest) throws Exception {
        return CommonResponse.toResponseEntity(authService.authenticaeUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
        authService.registerUser(signUpRequest);
        return CommonResponse.toResponseEntity("", "User registered successfully");
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) throws Exception {
        return CommonResponse.toResponseEntity(authService.refreshtoken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) throws Exception {
        userRefreshTokenService.deleteByUsername(logOutRequest.getUsername());
        return CommonResponse.toResponseEntity("", "Log out successful");
    }

    @GetMapping("/forgetpass")
    public ResponseEntity<?> forgetPass(@RequestParam("userid") String userid, @RequestParam("emaild") String email) throws Exception {
        return CommonResponse.toResponseEntity(authService.forgetPass(userid, email));
    }

    @GetMapping("/resetpass")
    public ResponseEntity<?> resetPass(@RequestParam("token") String token) throws Exception {
        return CommonResponse.toResponseEntity(authService.resetPass(token));
    }

}
