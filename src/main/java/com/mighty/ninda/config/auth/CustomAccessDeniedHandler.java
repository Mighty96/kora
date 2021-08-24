package com.mighty.ninda.config.auth;

import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.domain.user.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        CurrentUser currentUser = CurrentUser.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (currentUser == null) {
            response.sendRedirect("/signin");
            return;
        }

        if (currentUser.getRole() == Role.GUEST) {
            response.sendRedirect("/signup_auth");
            return;
        }


        log.error("권한없는 사용자의 접근");
        response.sendRedirect("/");
    }
}
