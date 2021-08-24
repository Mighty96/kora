package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAnonymous()")
    @GetMapping("/signin")
    public String login() {

        return "auth/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @GetMapping("/signup_auth")
    public String auth(@LoginUser SessionUser user) {
        if (user.getRegistrationId() == RegistrationId.NINDA) {
            return "auth/signup_ninda";
        } else {
            return "auth/signup_oauth";
        }
    }

    @GetMapping("/authConfirm")
    public String authConfirm(@RequestParam String email, @RequestParam String authKey, Model model) {
        userService.authConfirm(email, authKey, model);
        httpSession.removeAttribute("user");
        return "redirect:/logout";
    }

    @PreAuthorize("hasAnyRole('ROLE_GUEST', 'ROLE_USER')")
    @GetMapping("/newPassword")
    public String newPassword() {
        return "auth/newPassword";
    }
}
