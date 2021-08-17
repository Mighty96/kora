package com.mighty.ninda.config.auth;

import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.domain.user.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        SessionUser user = (SessionUser)request.getSession().getAttribute("user");

        if (user.getRole() == Role.GUEST) {
            response.sendRedirect("/signup_auth");
            return;
        }

        response.sendRedirect("/");
    }
}
