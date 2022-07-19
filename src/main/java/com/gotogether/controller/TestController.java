package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.CommentRequest;
import com.gotogether.dto.request.PostRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@GetMapping("/all")
	public String allAccess() throws Exception {
//		throw new CustomException(ErrorCode.NEED_TO_LOGIN);
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public String userAccess() throws Exception {
		return "User Content.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String adminAccess() throws Exception {
		return "Admin Board.";
	}


	@PostMapping("/xss/")
	public ResponseEntity<?> xss(@Valid @RequestBody PostRequest postRequest, @ModelAttribute CommentRequest commentRequest) throws Exception {
		log.debug("########### PostRequest : "+postRequest.toString());
		log.debug("########### CommentRequest : "+commentRequest.toString());
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(postRequest);
		result.add(commentRequest);
		return CommonResponse.toResponseEntity(result);
	}
}
