package com.mighty.kora.controller;

import com.mighty.kora.config.auth.LoginUser;
import com.mighty.kora.config.auth.dto.SessionUser;
import com.mighty.kora.domain.user.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if (user != null && user.getRole() == Role.GUEST) {
            return "redirect:/signup_oauth";
        }
        if (user != null) {
            model.addAttribute("userNickname", user.getNickname());
        }

        return "index";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/signup_oauth")
    public String signup_oauth() {
        return "signup_oauth";
    }
}
