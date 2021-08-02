package com.gotogether.controller;

import com.gotogether.entity.User;
import com.gotogether.entity.UserActiveToken;
import com.gotogether.service.UserActiveTokenService;
import com.gotogether.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	private final UserActiveTokenService userActiveTokenService;

	@GetMapping("/sign-in")
	String signIn() {
		return "sign-in";
	}

	@GetMapping("/sign-up")
	String signUpPage(User user) {
		return "sign-up";
	}

	@PostMapping("/sign-up")
	String signUp(User user) {
		userService.signUpUser(user);
		return "redirect:/sign-in";
	}

	@GetMapping("/sign-up/user-active")
	String userActive(@RequestParam("token") String token) {
		Optional<UserActiveToken> optionalConfirmationToken = userActiveTokenService.findUserActiveTokenByToken(token);
		optionalConfirmationToken.ifPresent(userService::activeUser);
		return "redirect:/sign-in";
	}
}
