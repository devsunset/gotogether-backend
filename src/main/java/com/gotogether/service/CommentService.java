package com.gotogether.service;

import com.gotogether.dto.request.CommentRequest;
import com.gotogether.entity.Comment;
import com.gotogether.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService{

    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final ModelMapper modelMapper;

    public Long save(CommentRequest commentRequest) throws Exception {
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        log.debug("################## : "+comment.toString());
        comment.setWriter(authService.getSessionUser());
        return commentRepository.save(comment).getComment_id();
    }

    public Long update(Long comment_id, CommentRequest commentRequest) throws Exception {
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        comment.setWriter(authService.getSessionUser());
        return commentRepository.save(comment).getComment_id();
    }

    public void delete(Long comment_id) {
        commentRepository.deleteById(comment_id);
    }
}
