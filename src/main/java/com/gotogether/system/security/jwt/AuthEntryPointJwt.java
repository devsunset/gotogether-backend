package com.gotogether.system.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotogether.system.constants.Constants;
import com.gotogether.system.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("result", Constants.Result.ERROR);
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("code", ErrorCode.UNAUTHORIZED);
        body.put("error", ErrorCode.UNAUTHORIZED.getDescription());
        body.put("message", authException.getMessage());
        body.put("description", request.getServletPath());
        body.put("data", "");
        body.put("timestamp", LocalDateTime.now().toString());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}
