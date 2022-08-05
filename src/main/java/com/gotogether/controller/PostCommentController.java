package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.PostCommentRequest;
import com.gotogether.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/postcomment")
@RequiredArgsConstructor
public class PostCommentController {

    @Autowired
    private final PostCommentService postCommentService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody PostCommentRequest postCommentRequest) throws Exception {
        return CommonResponse.toResponseEntity(postCommentService.save(postCommentRequest));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{postCommentId}")
    public ResponseEntity<?> update(@PathVariable("postCommentId") Long postCommentId, @Valid @RequestBody PostCommentRequest postCommentRequest) throws Exception {
        return CommonResponse.toResponseEntity(postCommentService.update(postCommentId, postCommentRequest));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{postCommentId}")
    public ResponseEntity<?> delete(@PathVariable("postCommentId") Long postCommentId) throws Exception {
        postCommentService.delete(postCommentId);
        return CommonResponse.toResponseEntity();
    }

    @GetMapping("/list/{postId}")
    public ResponseEntity<?> getList(@PathVariable("postId") Long postId) throws Exception {
        return CommonResponse.toResponseEntity(postCommentService.getList(postId));
    }
}
