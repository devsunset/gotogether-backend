package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.CommentRequest;
import com.gotogether.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private final CommentService commentService;

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody CommentRequest commentRequest) throws Exception {
        return CommonResponse.toResponseEntity(commentService.save(commentRequest));
    }

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{commentId}")
    public ResponseEntity<?> update(@PathVariable("commentId") Long commentId, @Valid @RequestBody CommentRequest commentRequest) throws Exception {
        return CommonResponse.toResponseEntity(commentService.update(commentId,commentRequest));
    }

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable("commentId") Long commentId) throws Exception {
        commentService.delete(commentId);
        return CommonResponse.toResponseEntity();
    }

    @GetMapping("/list/{postId}")
    public ResponseEntity<?> getList(@PathVariable("postId") Long postId) throws Exception {
        return CommonResponse.toResponseEntity(commentService.getList(postId));
    }
}
