package com.gotogether.controller;

import com.gotogether.entity.Post;
import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.PostCreateRequest;
import com.gotogether.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @ModelAttribute PostCreateRequest postCreateRequest) throws Exception {
        return CommonResponse.toResponseEntity(postService.save(postCreateRequest));
    }

    @ResponseStatus(HttpStatus.OK)
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

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable("postId") Long postId) throws Exception {
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(Pageable pageable,
                       @ModelAttribute Post post) throws Exception {
//        return ResponseEntity.ok(postService.getPostList(pageable,post));
        return null;
    }

}
