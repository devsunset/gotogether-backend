package com.gotogether.service;

import com.gotogether.repository.TogetherCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TogetherCommentService {
    @Autowired
    private final TogetherCommentRepository togetherCommentRepository;

    @Autowired
    private final AuthService authService;

}