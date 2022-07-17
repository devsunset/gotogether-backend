package com.gotogether.service;

import com.gotogether.dto.request.CommentRequest;
import com.gotogether.entity.Comment;
import com.gotogether.entity.Post;
import com.gotogether.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService{

    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final ModelMapper modelMapper;

    public Long save(CommentRequest commentCreateRequest) throws Exception {
        Comment comment = modelMapper.map(commentCreateRequest, Comment.class);
        comment.setWriter(authService.getSessionUser());
        return commentRepository.save(comment).getComment_id();
    }

}
