package com.gotogether.service;

import com.gotogether.dto.request.CommentRequest;
import com.gotogether.dto.response.CommentResponse;
import com.gotogether.entity.Comment;
import com.gotogether.entity.Post;
import com.gotogether.entity.User;
import com.gotogether.repository.CommentRepository;
import com.gotogether.repository.PostRepository;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.exception.CustomException;
import com.gotogether.system.util.Utils;
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
public class CommentService {

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
        comment.setCommentId(null);
        comment.setWriter(authService.getSessionUser());
        return commentRepository.save(comment).getCommentId();
    }

    public Long update(Long commentId, CommentRequest commentRequest) throws Exception {
        User user = authService.getSessionUser();
        Comment orignal = commentRepository.findById(commentId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(Utils.isAdmin(user.getRoles()))) {
            if (!(user.getUsername().equals(orignal.getWriter().getUsername()))) {
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }

        Comment comment = modelMapper.map(commentRequest, Comment.class);
        comment.setCommentId(commentId);
        comment.setWriter(user);
        return commentRepository.save(comment).getCommentId();
    }

    public void delete(Long commentId) throws Exception {
        User user = authService.getSessionUser();
        Comment orignal = commentRepository.findById(commentId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(Utils.isAdmin(user.getRoles()))) {
            if (!(user.getUsername().equals(orignal.getWriter().getUsername()))) {
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }

        commentRepository.deleteById(commentId);
    }

    public List<CommentResponse> getList(Long postId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));
        List<Comment> comments = post.getComments();
        return comments.stream().map(CommentResponse::new).collect(Collectors.toList());
    }
}
