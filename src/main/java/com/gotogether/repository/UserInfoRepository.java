package com.gotogether.repository;

import com.gotogether.entity.User;
import com.gotogether.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByUser(User user);

    UserInfo[] findByIntroduceContainsIgnoreCaseOrNoteContainsIgnoreCaseOrGithubContainsIgnoreCaseOrHomepageContainsIgnoreCaseOrSkillContainsIgnoreCase(String searchWordIntroduce, String searchWordNote, String searchWordGithub, String searchWordHomepage, String searchWordSkill);

}
