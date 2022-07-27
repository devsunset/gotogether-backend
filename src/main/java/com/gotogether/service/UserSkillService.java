package com.gotogether.service;

import com.gotogether.dto.request.UserSkillRequest;
import com.gotogether.entity.Post;
import com.gotogether.entity.User;
import com.gotogether.entity.UserSkill;
import com.gotogether.repository.UserSkillRepository;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.exception.CustomException;
import com.gotogether.system.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private final ModelMapper modelMapper;

    public void save(UserSkillRequest[] userSkillRequest) throws Exception{
        User user = authService.getSessionUser();
        userSkillRepository.deleteByUser(user);

        for(UserSkillRequest userSkillParam: userSkillRequest){
            if(!Utils.isValidSkillLevelType(userSkillParam.getItemLevel())){
                throw new CustomException(ErrorCode.NOT_SKILL_LEVEL_TYPE);
            }
            UserSkill userSkill = modelMapper.map(userSkillParam, UserSkill.class);
            userSkill.setUser(user);
            userSkillRepository.save(userSkill);
        }
    }
}
