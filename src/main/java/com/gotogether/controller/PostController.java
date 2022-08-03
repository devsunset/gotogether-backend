package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.PostRequest;
import com.gotogether.dto.request.SearchCondition;
import com.gotogether.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private final PostService postService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody PostRequest postRequest) throws Exception {
        return CommonResponse.toResponseEntity(postService.save(postRequest));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{postId}")
    public ResponseEntity<?> update(@PathVariable("postId") Long postId, @Valid @RequestBody PostRequest postRequest) throws Exception {
        return CommonResponse.toResponseEntity(postService.update(postId, postRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updatecategory/{postId}")
    public ResponseEntity<?> changecategory(@PathVariable("postId") Long postId, @Valid @RequestBody PostRequest postRequest) throws Exception {
        return CommonResponse.toResponseEntity(postService.updatecategory(postId, postRequest));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable("postId") Long postId) throws Exception {
        postService.delete(postId);
        return CommonResponse.toResponseEntity();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> get(@PathVariable("postId") Long postId) throws Exception {
        return CommonResponse.toResponseEntity(postService.get(postId));
    }

    @PostMapping("/list")
    public ResponseEntity<?> getPageList(@PageableDefault(size = 10, sort = "postId", direction = Sort.Direction.DESC)
                                         Pageable pageable,
                                         @Valid @RequestBody SearchCondition searchCondition) throws Exception {
        return CommonResponse.toResponseEntity(postService.getPageList(pageable, searchCondition));
    }

}
