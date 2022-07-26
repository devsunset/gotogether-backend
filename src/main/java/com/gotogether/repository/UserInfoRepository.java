package com.gotogether.repository;

import com.gotogether.entity.User;
import com.gotogether.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

    UserInfo findByUser(User sessionUser);
}
