package com.gotogether.service;

import com.gotogether.dto.request.PostRequest;
import com.gotogether.dto.response.PostResponse;
import com.gotogether.entity.Post;
import com.gotogether.entity.User;
import com.gotogether.repository.PostRepository;
import com.gotogether.system.constants.Constants;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.exception.CustomException;
import com.gotogether.system.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final ModelMapper modelMapper;

    public Long save(PostRequest postRequest) throws Exception {
        if(!(Constants.PostType.TALK.equals(postRequest.getCategory()) || Constants.PostType.QA.equals(postRequest.getCategory()))){
            throw new CustomException(ErrorCode.NOT_POST_TYPE);
        }
        Post post = modelMapper.map(postRequest, Post.class);
        post.setWriter(authService.getSessionUser());
        return postRepository.save(post).getPost_id();
    }

    public Long update(Long post_id, PostRequest postRequest) throws Exception {
        if(!(Utils.isValidPostType(postRequest.getCategory()))){
            throw new CustomException(ErrorCode.NOT_POST_TYPE);
        }
        User user = authService.getSessionUser();
        Post orignal = postRepository.findById(post_id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_POST));

        if(!(Utils.isAdmin(user.getRoles()))){
            if(!(user.getUsername().equals(orignal.getWriter().getUsername()))){
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }

        Post post = modelMapper.map(postRequest, Post.class);
        post.setPost_id(post_id);
        post.setDeleted(Constants.NO);
        post.setWriter(authService.getSessionUser());
        return postRepository.save(post).getPost_id();
    }

    public Long changecategory(Long post_id, PostRequest postRequest) throws Exception {
        if(!(Utils.isValidPostType(postRequest.getCategory()))){
            throw new CustomException(ErrorCode.NOT_POST_TYPE);
        }

        User user = authService.getSessionUser();
        Post post = postRepository.findById(post_id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_POST));

        if(!(Utils.isAdmin(user.getRoles()))){
            throw new CustomException(ErrorCode.NOT_ROLE_ADMIN);
        }

        post.setCategory(postRequest.getCategory());
        return postRepository.save(post).getPost_id();
    }

    public void delete(Long post_id) throws Exception {
        User user = authService.getSessionUser();
        Post post = postRepository.findById(post_id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_POST));

        if(!(Utils.isAdmin(user.getRoles()))){
            if(!(user.getUsername().equals(post.getWriter().getUsername()))){
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }
        postRepository.deleteById(post_id);
    }

    public PostResponse get(Long post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_POST));
        return new PostResponse(post);
    }
}
