package com.mighty.ninda.config.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("가입되지 않은 사용자 접근");
        request.setAttribute("message", "로그인이 필요한 서비스에요.");
        request.setAttribute("url", "/signin");
        request.setAttribute("status", "error");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/message");
        dispatcher.forward(request, response);
    }
}
