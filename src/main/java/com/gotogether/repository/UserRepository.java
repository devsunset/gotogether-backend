package com.gotogether.repository;

import com.gotogether.entity.Post;
import com.gotogether.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Boolean existsByNickname(String userid);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  User[] findByUsernameContainsIgnoreCaseOrNicknameContainsIgnoreCase(String searchWordUsername,String searchWordNickname);

  Page<User> findByUsernameIn(String[] users, Pageable pageable);

}
