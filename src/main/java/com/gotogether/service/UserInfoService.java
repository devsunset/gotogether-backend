package com.gotogether.service;

import com.gotogether.dto.request.SearchCondition;
import com.gotogether.dto.request.UserInfoRequest;
import com.gotogether.dto.response.UserInfoResponse;
import com.gotogether.entity.User;
import com.gotogether.entity.UserInfo;
import com.gotogether.repository.UserInfoRepository;
import com.gotogether.repository.UserRepository;
import com.gotogether.system.constants.Constants;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.exception.CustomException;
import com.gotogether.system.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {
    @Autowired
    private final UserInfoRepository userInfoRepository;


    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final ModelMapper modelMapper;

    public Long save(UserInfoRequest userInfoRequest) throws Exception {
        checkSkillLevelType(userInfoRequest.getSkill());
        UserInfo userInfo = userInfoRepository.findByUser(authService.getSessionUser());
        if (userInfo == null) {
            userInfo = modelMapper.map(userInfoRequest, UserInfo.class);
            userInfo.setUser(authService.getSessionUser());
        } else {
            userInfo.setIntroduce(userInfoRequest.getIntroduce());
            userInfo.setNote(userInfoRequest.getNote());
            userInfo.setGithub(userInfoRequest.getGithub());
            userInfo.setHomepage(userInfoRequest.getHomepage());
            userInfo.setSkill(userInfoRequest.getSkill());
            userInfo.setUser(authService.getSessionUser());
        }
        return userInfoRepository.save(userInfo).getUserInfoId();
    }

    private void checkSkillLevelType(String skill) throws Exception {
        if (!("".equals(skill) || skill == null)) {
            String[] skills = skill.split(Constants.SEPARATOR_PIFE);
            for (String item : skills) {
                if (!Utils.isValidSkillLevelType(item.split(Constants.SEPARATOR_ANGLE_BRACKET)[1])) {
                    throw new CustomException(ErrorCode.INVALID_SKILL_LEVEL_TYPE);
                }
            }
        }
    }

    public UserInfoResponse getSessionByUserInfo() throws Exception {
        User user = authService.getSessionUser();
        if (user != null) {
            UserInfo userInfo = userInfoRepository.findByUser(user);
            if (userInfo != null) {
                return new UserInfoResponse(userInfo);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public UserInfoResponse getUserIdByUserInfo(String userId) throws Exception {
        User user = authService.getUserOrEmptyNull(userId);
        if (user != null) {
            UserInfo userInfo = userInfoRepository.findByUser(user);
            if (userInfo != null) {
                return new UserInfoResponse(userInfo);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private User[] getUserSearch(String searchWord) throws Exception {
        return userRepository.findByUsernameContainsIgnoreCaseOrNicknameContainsIgnoreCase(searchWord, searchWord);
    }

    private UserInfo[] getUserInfoSearch(String searchWord) throws Exception {
        return userInfoRepository.findByIntroduceContainsIgnoreCaseOrNoteContainsIgnoreCaseOrGithubContainsIgnoreCaseOrHomepageContainsIgnoreCaseOrSkillContainsIgnoreCase(searchWord, searchWord, searchWord, searchWord, searchWord);
    }

    private UserInfo getUserInfo(String userId) throws Exception {
        User user = authService.getUserOrEmptyNull(userId);
        if (user != null) {
            return userInfoRepository.findByUser(user);
        } else {
            return null;
        }
    }


    public Page<UserInfoResponse> getPageList(Pageable pageable, SearchCondition searchCondition) throws Exception {
        ArrayList<User> userList = new ArrayList<User>();

        if ("".equalsIgnoreCase(searchCondition.getKeyword().trim())) {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                userList.add(user);
            }
        } else {
            User[] users = this.getUserSearch(searchCondition.getKeyword());
            for (User user : users) {
                userList.add(user);
            }

            UserInfo[] userInfos = this.getUserInfoSearch(searchCondition.getKeyword());
            for (UserInfo userInfo : userInfos) {
                userList.add(userInfo.getUser());
            }
        }

        TreeSet<String> params = new TreeSet<String>();
        for (User user : userList) {
            params.add(user.getUsername());
        }
        String[] userIds = params.toArray(new String[params.size()]);

        Page<UserInfoResponse> page = userRepository.findByUsernameIn(userIds, pageable).map(UserInfoResponse::new);
        page = page.map(this::transformResponse);
        return page;
    }

    private UserInfoResponse transformResponse(final UserInfoResponse userInfoResponse) {
        UserInfo userInfo = null;
        try {
            userInfo = this.getUserInfo(userInfoResponse.getUsername());
        } catch (Exception e) {
            log.error("ERROR", e);
        }

        if (userInfo != null) {
            userInfoResponse.setUserInfoId(userInfo.getUserInfoId());
            userInfoResponse.setIntroduce(userInfo.getIntroduce());
            userInfoResponse.setNote(userInfo.getNote());
            userInfoResponse.setGithub(userInfo.getGithub());
            userInfoResponse.setHomepage(userInfo.getHomepage());
            userInfoResponse.setSkill(userInfo.getSkill());
        }
        return userInfoResponse;
    }

}
