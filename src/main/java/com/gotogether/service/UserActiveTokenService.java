package com.gotogether.service;

import com.gotogether.entity.UserActiveToken;
import com.gotogether.repository.UserActiveTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserActiveTokenService {

	private final UserActiveTokenRepository userActiveTokenRepository;

	public void saveUserActiveToken(UserActiveToken userActiveToken) {
		userActiveTokenRepository.save(userActiveToken);
	}

	public void deleteUserActiveToken(Long id) {
		userActiveTokenRepository.deleteById(id);
	}

	public Optional<UserActiveToken> findUserActiveTokenByToken(String token) {
		return userActiveTokenRepository.findUserActiveTokenByUserActiveToken(token);
	}
}