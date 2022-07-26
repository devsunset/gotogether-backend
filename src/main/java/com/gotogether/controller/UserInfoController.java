package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.UserInfoRequest;
import com.gotogether.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/userinfo")
@RequiredArgsConstructor
public class UserInfoController {

    @Autowired
    private final UserInfoService userInfoService;

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody UserInfoRequest userInfoRequest) throws Exception {
        return CommonResponse.toResponseEntity(userInfoService.save(userInfoRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> get(@PathVariable("userId") String userId) throws Exception {
        return CommonResponse.toResponseEntity(userInfoService.get(userId));
    }

}
