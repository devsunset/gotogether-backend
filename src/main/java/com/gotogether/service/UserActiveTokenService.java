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

	public void saveUserActiveToken(UserActiveToken userActiveToken) throws Exception {
		userActiveTokenRepository.save(userActiveToken);
	}

	public void deleteUserActiveToken(Long id) throws Exception {
		userActiveTokenRepository.deleteById(id);
	}

	public Optional<UserActiveToken> findUserActiveTokenByToken(String token)  throws Exception {
		return userActiveTokenRepository.findUserActiveTokenByUserActiveToken(token);
	}
}