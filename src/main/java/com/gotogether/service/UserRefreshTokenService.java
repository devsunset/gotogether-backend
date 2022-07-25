package com.gotogether.service;

import com.gotogether.entity.UserRefreshToken;
import com.gotogether.repository.UserRefreshTokenRepository;
import com.gotogether.repository.UserRepository;
import com.gotogether.system.exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Configuration
public class UserRefreshTokenService {
  @Value("${go.together.jwtRefreshExpirationMs}")
  private Long userRefreshTokenDurationMs;

  @Autowired
  private UserRefreshTokenRepository userRefreshTokenRepository;

  @Autowired
  private UserRepository userRepository;

  public Optional<UserRefreshToken> findByToken(String token) throws Exception {
    return userRefreshTokenRepository.findByToken(token);
  }

  public UserRefreshToken createRefreshToken(String username) throws Exception {
    UserRefreshToken userRefreshToken = new UserRefreshToken();

    userRefreshToken.setUser(userRepository.findByUsername(username).get());
    userRefreshToken.setExpiryDate(Instant.now().plusMillis(userRefreshTokenDurationMs));
    userRefreshToken.setToken(UUID.randomUUID().toString());

    userRefreshToken = userRefreshTokenRepository.save(userRefreshToken);
    return userRefreshToken;
  }

  public UserRefreshToken verifyExpiration(UserRefreshToken token){
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      userRefreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
    }
    return token;
  }

  public int deleteByUsername(String username) throws Exception {
    return userRefreshTokenRepository.deleteByUser(userRepository.findByUsername(username).get());
  }
}
