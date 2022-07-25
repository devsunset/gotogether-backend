package com.gotogether.service;

import com.gotogether.repository.TogetherSkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TogetherSkillService {
    @Autowired
    private final TogetherSkillRepository togetherSkillRepository;

    @Autowired
    private final AuthService authService;

}
