package com.gotogether.service;

import com.gotogether.repository.CommentRepository;
import com.gotogether.repository.PostRepository;
import com.gotogether.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService{

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

}
