package com.gotogether.service;

import com.gotogether.dto.request.CommentRequest;
import com.gotogether.dto.response.CommentResponse;
import com.gotogether.entity.Comment;
import com.gotogether.entity.Post;
import com.gotogether.entity.User;
import com.gotogether.repository.CommentRepository;
import com.gotogether.repository.PostRepository;
import com.gotogether.system.constants.Constants;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService{

    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final ModelMapper modelMapper;

    public Long save(CommentRequest commentRequest) throws Exception {
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        comment.setComment_id(null);
        comment.setWriter(authService.getSessionUser());
        return commentRepository.save(comment).getComment_id();
    }

    public Long update(Long comment_id, CommentRequest commentRequest) throws Exception {
        User user = authService.getSessionUser();
        Comment orignal = commentRepository.findById(comment_id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_POST));

        if(!user.getRoles().contains(Constants.ROLE_ADMIN)){
            if(!user.getUsername().equals(orignal.getWriter().getUsername())){
                new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }

        Comment comment = modelMapper.map(commentRequest, Comment.class);
        comment.setComment_id(comment_id);
        comment.setWriter(user);
        return commentRepository.save(comment).getComment_id();
    }

    public void delete(Long comment_id) throws Exception {
        User user = authService.getSessionUser();
        Comment orignal = commentRepository.findById(comment_id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_POST));

        if(!user.getRoles().contains(Constants.ROLE_ADMIN)){
            if(!user.getUsername().equals(orignal.getWriter().getUsername())){
                new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }

        commentRepository.deleteById(comment_id);
    }

    public  List<CommentResponse> getList(Long post_id) throws Exception {
        Post post = postRepository.findById(post_id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_POST));
        List<Comment> comments = post.getComments();
        return comments.stream().map(CommentResponse::new).collect(Collectors.toList());
    }
}
