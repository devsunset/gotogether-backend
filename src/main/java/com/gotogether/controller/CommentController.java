package com.gotogether.controller;

import com.gotogether.entity.Comment;
import com.gotogether.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity save(@PathVariable("postId") Long postId
                            , Comment comment) throws Exception {
        //commentService.save(postId, comment);
        return null;
    }

    @PutMapping("/{commentId}")
    public ResponseEntity update(@PathVariable("commentId") Long commentId,
                       Comment comment) throws Exception {
        //commentService.update(commentId, comment);
        return null;
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity delete(@PathVariable("commentId") Long commentId) throws Exception {
        //commentService.remove(commentId);
        return null;
    }
}
