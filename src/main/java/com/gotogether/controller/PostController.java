package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.PostRequest;
import com.gotogether.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private final PostService postService;

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody PostRequest postRequest) throws Exception {
        return CommonResponse.toResponseEntity(postService.save(postRequest));
    }

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{post_id}")
    public ResponseEntity<?> update(@PathVariable("post_id") Long post_id, @Valid @RequestBody PostRequest postRequest) throws Exception {
        return CommonResponse.toResponseEntity(postService.update(post_id,postRequest));
    }

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{post_id}")
    public ResponseEntity<?> delete(@PathVariable("post_id") Long post_id) throws Exception {
        postService.delete(post_id);
        return CommonResponse.toResponseEntity();
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<?> getDetail(@PathVariable("post_id") Long post_id) throws Exception {
        return null;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getPageList(Pageable pageable,
                                    @Valid @RequestBody PostRequest postRequest) throws Exception {
        return null;
    }

}
