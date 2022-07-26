package com.gotogether.service;

import com.gotogether.entity.Role;
import com.gotogether.entity.User;
import com.gotogether.entity.UserActiveToken;
import com.gotogether.entity.UserRefreshToken;
import com.gotogether.dto.request.LogInRequest;
import com.gotogether.dto.request.SignUpRequest;
import com.gotogether.dto.request.TokenRefreshRequest;
import com.gotogether.dto.response.JwtResponse;
import com.gotogether.dto.response.TokenRefreshResponse;
import com.gotogether.repository.RoleRepository;
import com.gotogether.repository.UserRepository;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.enums.RoleType;
import com.gotogether.system.exception.CustomException;
import com.gotogether.system.exception.TokenRefreshException;
import com.gotogether.system.security.jwt.JwtUtils;
import com.gotogether.system.security.service.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
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
    UserRefreshTokenService userRefreshTokenService;

    @Autowired
    UserActiveTokenService userActiveTokenService;

    @Autowired
    EmailSenderService emailSenderService;


    public JwtResponse authenticaeUser(LogInRequest loginRequest) throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserRefreshToken userRefreshToken = userRefreshTokenService.createRefreshToken(userDetails.getUsername());

        return new JwtResponse(jwt, userRefreshToken.getToken(), userDetails.getUsername(),
                userDetails.getNickname(), userDetails.getEmail(), roles);
    }

    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) throws Exception {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new CustomException(ErrorCode.USER_ALREADY_USE);
        }

        if (userRepository.existsByNickname(signUpRequest.getNickname())) {
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_USE);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_USE);
        }

        User user = new User(signUpRequest.getUsername(),signUpRequest.getNickname(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRolename(RoleType.ROLE_GUEST)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ROLE));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRolename(RoleType.ROLE_ADMIN)
                                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ROLE));
                        roles.add(adminRole);

                        break;
                    case "user":
                        Role userRole = roleRepository.findByRolename(RoleType.ROLE_USER)
                                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ROLE));
                        roles.add(userRole);

                        break;
                    default:
                        Role notApproveRole = roleRepository.findByRolename(RoleType.ROLE_GUEST)
                                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ROLE));
                        roles.add(notApproveRole);
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

    public TokenRefreshResponse refreshtoken(TokenRefreshRequest request) throws Exception {
        String requestRefreshToken = request.getRefreshToken();

        return userRefreshTokenService.findByToken(requestRefreshToken)
                .map(userRefreshTokenService::verifyExpiration)
                .map(UserRefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());

                    List<String> roles = new ArrayList<String>();

                    for (Role element :user.getRoles()) {
                        roles.add(element.getRolename().toString());
                    }

                    return new TokenRefreshResponse(token, requestRefreshToken, user.getUsername(), user.getNickname(),user.getEmail(),roles);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    public void activeUser (String token) throws Exception {
        Optional<UserActiveToken> userActiveToken = userActiveTokenService.findUserActiveTokenByToken(token);
        final User user = userActiveToken.get().getUser();

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByRolename(RoleType.ROLE_USER)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ROLE));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);
        userActiveTokenService.deleteUserActiveToken(userActiveToken.get().getActiveId());
    }

    public void sendUserActiveMail(String userMail, String token) throws Exception {
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

    public User getSessionUser() throws Exception {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetail.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetail.getUsername()));
        return user;
    }

    public User getUser(String username) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return user;
    }

    public User getUserOrEmptyNull(String username) throws Exception {
         return userRepository.findByUsername(username).orElse(null);
    }

    public String getSessionUsername() throws Exception {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail.getUsername();
    }

    public String getSessionUserRole() throws Exception {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetail.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetail.getUsername()));
        String rolename = RoleType.ROLE_GUEST.toString();
        for (Role element :user.getRoles()) {
            rolename = element.getRolename().toString();
        }
        return rolename;
    }

}
