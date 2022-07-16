package com.gotogether.controller;

import com.gotogether.entity.Post;
import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.PostCreateRequest;
import com.gotogether.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasRole('ROLE_NOT_APPROVE') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody PostCreateRequest postCreateRequest) throws Exception {
        return CommonResponse.toResponseEntity(postService.save(postCreateRequest));
    }

    @PreAuthorize("hasRole('ROLE_NOT_APPROVE') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{postId}")
    public ResponseEntity<?> update(@PathVariable("postId") Long postId,
                       @ModelAttribute Post post) throws Exception {
        return null;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> get(@PathVariable("postId") Long postId) throws Exception {
//        return ResponseEntity.ok(postService.getPostInfo(postId));
        return null;
    }

    @PreAuthorize("hasRole('ROLE_NOT_APPROVE') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable("postId") Long postId) throws Exception {
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(Pageable pageable,
                                    @Valid @RequestBody Post post) throws Exception {
//        return ResponseEntity.ok(postService.getPostList(pageable,post));
        return null;
    }

}
