package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.TogetherCommentRequest;
import com.gotogether.service.TogetherCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/togethercomment")
@RequiredArgsConstructor
public class TogetherCommentController {

    @Autowired
    private final TogetherCommentService togetherCommentService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody TogetherCommentRequest togetherCommentRequest) throws Exception {
        return CommonResponse.toResponseEntity(togetherCommentService.save(togetherCommentRequest));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{togetherCommentId}")
    public ResponseEntity<?> update(@PathVariable("togetherCommentId") Long togetherCommentId, @Valid @RequestBody TogetherCommentRequest togetherCommentRequest) throws Exception {
        return CommonResponse.toResponseEntity(togetherCommentService.update(togetherCommentId, togetherCommentRequest));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{togetherCommentId}")
    public ResponseEntity<?> delete(@PathVariable("togetherCommentId") Long commentId) throws Exception {
        togetherCommentService.delete(commentId);
        return CommonResponse.toResponseEntity();
    }

    @GetMapping("/list/{togetherId}")
    public ResponseEntity<?> getList(@PathVariable("togetherId") Long togetherId) throws Exception {
        return CommonResponse.toResponseEntity(togetherCommentService.getList(togetherId));
    }
}
