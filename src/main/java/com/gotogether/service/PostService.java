package com.gotogether.service;

import com.gotogether.entity.Post;
import com.gotogether.entity.User;
import com.gotogether.payload.request.PostCreateRequest;
import com.gotogether.repository.PostRepository;
import com.gotogether.repository.UserRepository;
import com.gotogether.system.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final AuthService authService;

    @Autowired
    private final ModelMapper modelMapper;

    public Long save(PostCreateRequest postCreateRequest) throws Exception {
        Post post = modelMapper.map(postCreateRequest, Post.class);
        post.setWriter(authService.getSessionUserFromJwt());
        log.debug("#################post###############"+post.toString());
        return postRepository.save(post).getPost_id();
    }
}
