package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.service.OneLineCommentService;
import com.mighty.ninda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final OneLineCommentService oneLineCommentService;

    @GetMapping("/user/profile")
    public String profile(@LoginUser SessionUser sessionUser, Model model) {
        model.addAttribute("user", userService.findById(sessionUser.getId()));
        return "user/profile";
    }

    @GetMapping("/user/oneLineComment")
    public String oneLineComment(@LoginUser SessionUser sessionUser, Model model) {


        model.addAttribute("oneLineComments", oneLineCommentService.findOneLineCommentByUserId(sessionUser.getId()));

        return "user/oneLineComment";
    }
}
