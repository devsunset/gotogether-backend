package com.gotogether.controller;

import com.gotogether.entity.Post;
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
    public void save(@Valid @ModelAttribute Post post){
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{postId}")
    public void update(@PathVariable("postId") Long postId,
                       @ModelAttribute Post post){
    }

    @GetMapping("/{postId}")
    public ResponseEntity getInfo(@PathVariable("postId") Long postId){
//        return ResponseEntity.ok(postService.getPostInfo(postId));
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{postId}")
    public void delete(@PathVariable("postId") Long postId){
    }

    @GetMapping("/post")
    public ResponseEntity search(Pageable pageable,
                       @ModelAttribute Post post){
//        return ResponseEntity.ok(postService.getPostList(pageable,post));
        return null;
    }

}
