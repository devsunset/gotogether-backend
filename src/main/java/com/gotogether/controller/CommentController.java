package com.gotogether.controller;

import com.gotogether.entity.Comment;
import com.gotogether.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void commentSave(@PathVariable("postId") Long postId
                            , Comment comment){
        //commentService.save(postId, comment);
    }

    @PutMapping("/{commentId}")
    public void update(@PathVariable("commentId") Long commentId,
                       Comment comment){
        //commentService.update(commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId){
        //commentService.remove(commentId);
    }
}
