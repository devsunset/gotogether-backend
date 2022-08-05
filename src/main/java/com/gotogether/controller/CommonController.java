package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.PostCommentRequest;
import com.gotogether.dto.request.PostRequest;
import com.gotogether.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    CommonService commonService;

    @GetMapping("/home")
    public ResponseEntity<?> home() throws Exception {
        return CommonResponse.toResponseEntity(commonService.home());
    }


    ///////////////////////////////////////////////////////////////////
    @GetMapping("/test/all")
    public String allAccess() throws Exception {
        return "Public Content.";
    }

    @GetMapping("/test/user")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public String userAccess() throws Exception {
        return "User Content.";
    }

    @GetMapping("/test/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAccess() throws Exception {
        return "Admin Content.";
    }


    @PostMapping("/test/xss/")
    public ResponseEntity<?> xss(@Valid @RequestBody PostRequest postRequest, @ModelAttribute PostCommentRequest commentRequest) throws Exception {
        log.debug("########### PostRequest : " + postRequest.toString());
        log.debug("########### CommentRequest : " + commentRequest.toString());
        ArrayList<Object> result = new ArrayList<Object>();
        result.add(postRequest);
        result.add(commentRequest);
        return CommonResponse.toResponseEntity(result);
    }
    ///////////////////////////////////////////////////////////////////
}
