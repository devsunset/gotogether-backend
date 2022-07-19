package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.CommentRequest;
import com.gotogether.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody CommentRequest commentRequest) throws Exception {
        return CommonResponse.toResponseEntity(commentService.save(commentRequest));
    }

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{comment_id}")
    public ResponseEntity<?> update(@PathVariable("comment_id") Long comment_id, @Valid @RequestBody CommentRequest commentRequest) throws Exception {
        return CommonResponse.toResponseEntity(commentService.update(comment_id,commentRequest));
    }

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{comment_id}")
    public ResponseEntity<?> delete(@PathVariable("comment_id") Long comment_id) throws Exception {
        commentService.delete(comment_id);
        return CommonResponse.toResponseEntity();
    }

    @GetMapping("/list/{post_id}")
    public ResponseEntity<?> getList(@PathVariable("post_id") Long post_id) throws Exception {
        return CommonResponse.toResponseEntity(commentService.getList(post_id));
    }
}
