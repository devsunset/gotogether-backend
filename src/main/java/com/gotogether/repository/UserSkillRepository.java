package com.gotogether.repository;

import com.gotogether.entity.User;
import com.gotogether.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long>{

    int deleteByUser(User user);

    UserSkill [] findByItemContainsIgnoreCase(String searchWord);
}
