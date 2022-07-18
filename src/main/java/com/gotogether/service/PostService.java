package com.gotogether.service;

import com.gotogether.dto.request.PostRequest;
import com.gotogether.entity.Post;
import com.gotogether.repository.PostRepository;
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

    public Long save(PostRequest postCreateRequest) throws Exception {
        Post post = modelMapper.map(postCreateRequest, Post.class);
        post.setWriter(authService.getSessionUser());
        return postRepository.save(post).getPost_id();
    }

    public void delete(Long post_id) throws Exception {
        postRepository.deleteById(post_id);
    }
}
