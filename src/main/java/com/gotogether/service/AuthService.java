package com.gotogether.service;

import com.gotogether.entity.RefreshToken;
import com.gotogether.entity.Role;
import com.gotogether.entity.RoleType;
import com.gotogether.entity.User;
import com.gotogether.entity.UserActiveToken;
import com.gotogether.repository.RoleRepository;
import com.gotogether.repository.UserRepository;
import com.gotogether.system.exception.TokenRefreshException;
import com.gotogether.system.security.jwt.JwtUtils;
import com.gotogether.system.security.payload.request.LoginRequest;
import com.gotogether.system.security.payload.request.SignupRequest;
import com.gotogether.system.security.payload.request.TokenRefreshRequest;
import com.gotogether.system.security.payload.response.JwtResponse;
import com.gotogether.system.security.payload.response.MessageResponse;
import com.gotogether.system.security.payload.response.TokenRefreshResponse;
import com.gotogether.system.security.service.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserActiveTokenService userActiveTokenService;

    @Autowired
    EmailSenderService emailSenderService;


    public ResponseEntity<JwtResponse> authenticaeUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        //To-Do (isEnable Check)
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    public ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: UserId is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(RoleType.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        UserActiveToken userActiveToken = new UserActiveToken(user);
        userActiveTokenService.saveUserActiveToken(userActiveToken);
        sendUserActiveMail(user.getEmail(), userActiveToken.getUserActiveToken());

        return null;
    }

    public ResponseEntity<TokenRefreshResponse> refreshtoken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    public void activeUser (String token){
        Optional<UserActiveToken> userActiveToken = userActiveTokenService.findUserActiveTokenByToken(token);
        final User user = userActiveToken.get().getUser();
        user.setEnabled("Y");
        userRepository.save(user);
        userActiveTokenService.deleteUserActiveToken(userActiveToken.get().getActive_id());
    }

    public void sendUserActiveMail(String userMail, String token) {
        // To-Do (Gmail security setting)
        log.info(token);
        /*
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("gotogether User Account Active Mail Confirmation Link");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText("Thank you for registering. Please click on the below link to activate your account."
                + "http://localhost:8081/api/auth/signup/activeuser?token="
                + token);

        emailSenderService.sendEmail(mailMessage);
        */
    }
}
