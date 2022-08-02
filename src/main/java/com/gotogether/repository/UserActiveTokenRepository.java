package com.gotogether.repository;

import com.gotogether.entity.UserActiveToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserActiveTokenRepository extends JpaRepository<UserActiveToken, Long> {
    Optional<UserActiveToken> findUserActiveTokenByUserActiveToken(String token);
}
