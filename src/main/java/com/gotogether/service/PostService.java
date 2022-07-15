package com.gotogether.service;

import com.gotogether.entity.Post;
import com.gotogether.payload.request.PostCreateRequest;
import com.gotogether.repository.PostRepository;
import com.gotogether.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;


@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public ResponseEntity<?> save(PostCreateRequest postCreateRequest) {
        log.debug("#################postCreateRequest###############"+postCreateRequest.toString());
        Post post = modelMapper.map(postCreateRequest, Post.class);
        log.debug("#################post###############"+post.toString());
        return null;
    }
}
