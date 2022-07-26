package com.gotogether.service;

import com.gotogether.dto.request.UserInfoRequest;
import com.gotogether.dto.response.UserInfoResponse;
import com.gotogether.entity.UserInfo;
import com.gotogether.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {
    @Autowired
    private final UserInfoRepository userInfoRepository;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final ModelMapper modelMapper;

    public Long save(UserInfoRequest userInfoRequest) throws Exception {
        UserInfo userInfo = userInfoRepository.findByUser(authService.getSessionUser());
        if(userInfo == null){
            userInfo = modelMapper.map(userInfoRequest, UserInfo.class);
            userInfo.setUser(authService.getSessionUser());
        }else{
            userInfo.setIntroduce(userInfoRequest.getIntroduce());
            userInfo.setNote(userInfoRequest.getNote());
            userInfo.setGithub(userInfoRequest.getGithub());
            userInfo.setHomepage(userInfoRequest.getHomepage());
            userInfo.setUser(authService.getSessionUser());
        }
        return userInfoRepository.save(userInfo).getUserInfoId();
    }

    public UserInfoResponse get(String userId) throws Exception {
        return new UserInfoResponse(userInfoRepository.findByUser(authService.getUser(userId)));
    }
}
