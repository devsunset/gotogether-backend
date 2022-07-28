package com.gotogether.service;

import com.gotogether.dto.request.SearchCondition;
import com.gotogether.dto.request.UserInfoRequest;
import com.gotogether.dto.response.MembersResponse;
import com.gotogether.dto.response.UserInfoResponse;
import com.gotogether.entity.User;
import com.gotogether.entity.UserInfo;
import com.gotogether.entity.UserSkill;
import com.gotogether.repository.UserInfoRepository;
import com.gotogether.repository.UserRepository;
import com.gotogether.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    private final UserSkillRepository userSkillRepository;

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
        User user = authService.getUserOrEmptyNull(userId);
        if(user != null){
            UserInfo userInfo = userInfoRepository.findByUser(user);
            if(userInfo != null){
                return new UserInfoResponse(userInfo);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }




    private User[] getUserSearch(String searchWord)  throws Exception{
        return userRepository.findByUsernameContainsIgnoreCaseOrNicknameContainsIgnoreCase(searchWord,searchWord);
    }
    private UserInfo[] getUserInfoSearch(String searchWord)  throws Exception{
        return userInfoRepository.findByIntroduceContainsIgnoreCase(searchWord);
    }
    private UserSkill[] getUserSkillSearch(String searchWord)  throws Exception{
        return userSkillRepository.findByItemContainsIgnoreCase(searchWord);
    }

    private UserInfo getUserInfo(String userId)  throws Exception{
        User user = authService.getUserOrEmptyNull(userId);
        if(user != null){
           return userInfoRepository.findByUser(user);
        }else{
            return null;
        }
    }

    private List<HashMap<String,String>> getUserSkills(String userId)  throws Exception{
        ArrayList<HashMap<String,String>> item = new ArrayList<HashMap<String,String>>();
        User user = authService.getUserOrEmptyNull(userId);
        if(user != null){
            UserSkill[]  userSkills = userSkillRepository.findByUser(user);
            HashMap<String,String> map;
            for (UserSkill userSkill : userSkills) {
                map = new HashMap<String,String>();
                map.put("ITEM",userSkill.getItem());
                map.put("ITEM_LEVEL",userSkill.getItemLevel());
                item.add(map);
            }
            return item;
        }else{
            return null;
        }
    }

    public Page<MembersResponse> getPageList(Pageable pageable, SearchCondition searchCondition) throws Exception{
        ArrayList<User> userList = new ArrayList<User>();

        if("".equalsIgnoreCase(searchCondition.getKeyword().trim())){
            List<User> users = userRepository.findAll();
            for (User user : users) {
                userList.add(user);
            }
        }else{
            User[] users = this.getUserSearch(searchCondition.getKeyword());
            for (User user : users) {
                userList.add(user);
            }

            UserInfo[] userInfos = this.getUserInfoSearch(searchCondition.getKeyword());
            for (UserInfo userInfo : userInfos) {
                userList.add(userInfo.getUser());
            }

            UserSkill[] userSkills = this.getUserSkillSearch(searchCondition.getKeyword());
            for (UserSkill userSkill : userSkills) {
                userList.add(userSkill.getUser());
            }
        }

        TreeSet<String> params = new TreeSet<String>();
        for (User user : userList) {
            params.add(user.getUsername());
        }
        String[] userIds = params.toArray(new String[params.size()]);

        Page<MembersResponse> page = userRepository.findByUsernameIn(userIds,pageable).map(MembersResponse::new);
        page = page.map(this :: transformResponse);
        return page;
    }

    private MembersResponse transformResponse(final MembersResponse membersResponse){
        UserInfo userInfo = null;
        try{
            userInfo = this.getUserInfo(membersResponse.getUsername());
        }catch(Exception e){
            log.error("ERROR",e);
        }

        if(userInfo !=null){
            membersResponse.setIntroduce(userInfo.getIntroduce());
            membersResponse.setNote(userInfo.getNote());
            membersResponse.setGithub(userInfo.getGithub());
            membersResponse.setHomepage(userInfo.getHomepage());
            membersResponse.setProfileImageLink(userInfo.getProfileImageLink());
        }

        List<HashMap<String,String>> userSkill = null;
        try{
            userSkill  = this.getUserSkills(membersResponse.getUsername());
        }catch(Exception e){
            log.error("ERROR",e);
        }

        if(userSkill !=null){
            membersResponse.setSkills(userSkill);
        }
        return membersResponse;
    }

}
