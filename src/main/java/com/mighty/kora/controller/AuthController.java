package com.mighty.kora.controller;

import com.mighty.kora.config.auth.LoginUser;
import com.mighty.kora.config.auth.dto.SessionUser;
import com.mighty.kora.domain.user.RegistrationId;
import com.mighty.kora.domain.user.Role;
import com.mighty.kora.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final UserService userService;
    private final HttpSession httpSession;

    @GetMapping("/signin")
    public String login(@LoginUser SessionUser user) {

        //이미 로그인되어있다면 홈으로 리다이렉트
        if (user != null) {
            return "redirect:/";
        }

        return "auth/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }

    @GetMapping("/signup_auth")
    public String auth(@LoginUser SessionUser user) {
        if (user == null || user.getRole() != Role.GUEST) {
            return "redirect:/";
        }
        if (user.getRegistrationId() == RegistrationId.KORA) {
            return "auth/signup_kora";
        } else {
            return "auth/signup_oauth";
        }
    }

    @GetMapping("/authConfirm")
    public String authConfirm(@RequestParam String email, @RequestParam String authKey, Model model) {
        userService.authConfirm(email, authKey, model);
        httpSession.removeAttribute("user");
        return "auth/authConfirm";
    }
}
