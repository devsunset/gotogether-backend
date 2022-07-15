package com.gotogether.service;

import com.gotogether.entity.Post;
import com.gotogether.dto.request.PostCreateRequest;
import com.gotogether.repository.PostRepository;
import com.gotogether.system.constants.Constants;
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

    public Long save(PostCreateRequest postCreateRequest) throws Exception {
        Post post = modelMapper.map(postCreateRequest, Post.class);
        post.setWriter(authService.getSessionUserFromJwt());
        post.setDeleted(Constants.NO);
        return postRepository.save(post).getPost_id();
    }
}
