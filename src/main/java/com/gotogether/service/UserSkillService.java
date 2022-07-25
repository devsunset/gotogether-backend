package com.gotogether.service;

import com.gotogether.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSkillService {
    @Autowired
    private final UserSkillRepository userSkillRepository;

    @Autowired
    private final AuthService authService;

}
