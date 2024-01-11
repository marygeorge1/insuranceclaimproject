package com.sparta.insuranceclaim.config;

import com.sparta.insuranceclaim.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("login handler active");

        User user = (User) authentication.getPrincipal();
        String roles = user.getAuthorities().toString();

        String redirectURL = request.getContextPath();

        if (roles.contains("ROLE_USER")) {
            redirectURL = "/homepage/user";

        } else if (roles.contains("ROLE_ADMIN")) {
            redirectURL = "/homepage/admin";

        } else if (roles.contains("ROLE_AGENT")) {
            redirectURL = "/homepage/agent";

        } else {
            log.info("Redirection failed");
        }

        response.sendRedirect(redirectURL);

    }
}
