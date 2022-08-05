package com.gotogether.service;

import com.gotogether.dto.request.PostCommentRequest;
import com.gotogether.dto.response.PostCommentResponse;
import com.gotogether.entity.Post;
import com.gotogether.entity.PostComment;
import com.gotogether.entity.User;
import com.gotogether.repository.PostCommentRepository;
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
public class PostCommentService {

    @Autowired
    private final PostCommentRepository postCommentRepository;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final ModelMapper modelMapper;

    public Long save(PostCommentRequest postCommentRequest) throws Exception {
        PostComment postComment = modelMapper.map(postCommentRequest, PostComment.class);
        postComment.setPostCommentId(null);
        postComment.setWriter(authService.getSessionUser());
        return postCommentRepository.save(postComment).getPostCommentId();
    }

    public Long update(Long postCommentId, PostCommentRequest postCommentRequest) throws Exception {
        User user = authService.getSessionUser();
        PostComment orignal = postCommentRepository.findById(postCommentId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(Utils.isAdmin(user.getRoles()))) {
            if (!(user.getUsername().equals(orignal.getWriter().getUsername()))) {
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }

        PostComment postComment = modelMapper.map(postCommentRequest, PostComment.class);
        postComment.setPostCommentId(postCommentId);
        postComment.setWriter(user);
        return postCommentRepository.save(postComment).getPostCommentId();
    }

    public void delete(Long postCommentId) throws Exception {
        User user = authService.getSessionUser();
        PostComment orignal = postCommentRepository.findById(postCommentId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(Utils.isAdmin(user.getRoles()))) {
            if (!(user.getUsername().equals(orignal.getWriter().getUsername()))) {
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }

        postCommentRepository.deleteById(postCommentId);
    }

    public List<PostCommentResponse> getList(Long postId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));
        List<PostComment> comments = post.getComments();
        return comments.stream().map(PostCommentResponse::new).collect(Collectors.toList());
    }
}
