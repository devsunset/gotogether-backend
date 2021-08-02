package com.gotogether.service;

import com.gotogether.entity.User;
import com.gotogether.entity.UserActiveToken;
import com.gotogether.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final UserActiveTokenService userActiveTokenService;

	private final EmailSenderService emailSenderService;

	public void sendUserActiveMail(String userMail, String token) {
		final SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(userMail);
		mailMessage.setSubject("gotogether User Account Active Mail Confirmation Link");
		mailMessage.setFrom("<MAIL>");
		mailMessage.setText("Thank you for registering. Please click on the below link to activate your account."
						+ "http://localhost:8080/sign-up/user-active?token="
						+ token);
		log.info(mailMessage.toString());


		emailSenderService.sendEmail(mailMessage);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final Optional<User> optionalUser = userRepository.findByEmail(email);
		return optionalUser.orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email)));
	}

	public void signUpUser(User user) {
		final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		userRepository.save(user);
		final UserActiveToken userActiveToken = new UserActiveToken(user);
		userActiveTokenService.saveUserActiveToken(userActiveToken);
		sendUserActiveMail(user.getEmail(), userActiveToken.getUserActiveToken());
	}

	public void activeUser(UserActiveToken userActiveToken) {
		final User user = userActiveToken.getUser();
		user.setEnabled(true);
		userRepository.save(user);
		userActiveTokenService.deleteUserActiveToken(userActiveToken.getId());
	}
}
