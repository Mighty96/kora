package com.mighty.ninda.config.auth;

import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.domain.user.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Service
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CurrentUser currentUser = CurrentUser.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (currentUser.getRole() == Role.GUEST) {
            response.sendRedirect("/signup_auth");
            return;
        }

        response.sendRedirect("/");
    }
}
