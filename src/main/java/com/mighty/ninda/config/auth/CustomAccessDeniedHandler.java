package com.mighty.ninda.config.auth;

import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        CurrentUser currentUser = CurrentUser.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (currentUser == null) {
            request.setAttribute("message", "로그인이 필요한 서비스에요.");
            request.setAttribute("url", "/signin");
        } else if (currentUser.getRole() == Role.GUEST) {
            if (currentUser.getRegistrationId() == RegistrationId.NINDA) {
                request.setAttribute("message", "메일인증을 완료해주세요.");
            } else {
                request.setAttribute("message", "닉네임을 설정해주세요.");
            }
            request.setAttribute("url", "/signup_auth");
        } else {
            request.setAttribute("message", "접근권한이 없습니다.");
            request.setAttribute("url", "/");
        }
        request.setAttribute("status", "error");
        log.error("권한없는 사용자의 접근");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/message");
        dispatcher.forward(request, response);
    }
}
