package com.gotogether.service;

import com.gotogether.dto.request.LogInRequest;
import com.gotogether.dto.request.SignUpRequest;
import com.gotogether.dto.request.TokenRefreshRequest;
import com.gotogether.dto.response.JwtResponse;
import com.gotogether.dto.response.TokenRefreshResponse;
import com.gotogether.entity.Role;
import com.gotogether.entity.User;
import com.gotogether.entity.UserActiveToken;
import com.gotogether.entity.UserRefreshToken;
import com.gotogether.repository.RoleRepository;
import com.gotogether.repository.UserRepository;
import com.gotogether.system.constants.Constants;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

        User user = new User(signUpRequest.getUsername(), signUpRequest.getNickname(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role basicRole = roleRepository.findByRolename(RoleType.ROLE_USER)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ROLE));
        roles.add(basicRole);

        user.setRoles(roles);
        userRepository.save(user);

        UserActiveToken userActiveToken = new UserActiveToken(user);
        userActiveTokenService.saveUserActiveToken(userActiveToken);
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

                    for (Role element : user.getRoles()) {
                        roles.add(element.getRolename().toString());
                    }

                    return new TokenRefreshResponse(token, requestRefreshToken, user.getUsername(), user.getNickname(), user.getEmail(), roles);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
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
        String rolename = "";
        for (Role element : user.getRoles()) {
            rolename = element.getRolename().toString();
        }
        return rolename;
    }

    public String forgetPass(String userid, String email) throws Exception {
        User user = getUserOrEmptyNull(userid);
        if (user != null) {
            if (email.equals(user.getEmail())) {
                sendUserPassWordResetMail(user.getEmail());
                return Constants.YES;
            } else {
                return Constants.NO;
            }
        }
        return Constants.NO;
    }

    public void sendUserPassWordResetMail(String userMail) throws Exception {
        log.info("Forget Password Reset :" + userMail);
        // To-Do (Gmail security setting)
        // token 값 생성 로직 -> 임시 패스워드 포함 unique 값 조합으로 aes256암호화 값으로 생성
        /*
            final SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userMail);
            mailMessage.setSubject("Gotogether User Account Password Reset Link");
            mailMessage.setFrom("<MAIL>");
            mailMessage.setText("Link 클릭 후 임시 비밀번호로 로그인 후 비밀번호 변경 하시기 바랍니다. 임시 비밀번호  : RandomPassword"
            +"http://localhost:8081/api/auth/resetpassword?token=");
            emailSenderService.sendEmail(mailMessage);
        */
    }

    public String resetPass(String token) throws Exception {
        // To-Do (token parsing update temp password)
        return Constants.YES;
    }
}
