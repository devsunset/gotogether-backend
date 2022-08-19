package com.gotogether.service;

import com.gotogether.repository.PostRepository;
import com.gotogether.repository.TogetherRepository;
import com.gotogether.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    @Autowired
    private final TogetherService togetherService;

    @Autowired
    private final TogetherRepository togetherRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PostRepository postRepository;

    public HashMap<String, Object> home() throws Exception {
        DecimalFormat decFormat = new DecimalFormat("###,###");
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("TOGETHER", decFormat.format(togetherRepository.count()));
        result.put("USER", decFormat.format(userRepository.count()));
        result.put("TALK", decFormat.format(postRepository.countByCategory("TALK")));
        result.put("QA", decFormat.format(postRepository.countByCategory("QA")));
        result.put("RECENT_TOGETHER", togetherService.getRecentList());
        result.put("NOTICE", "함꼐 공부 해요<br>로그인 없이도 기본 조회는 가능 합니다.<br>프로필 작성해 보세요 누군가 나를 찾을지도 몰라요 ^^");
        return result;
    }
}
