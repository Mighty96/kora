package com.mighty.kora.controller;

import com.mighty.kora.config.auth.LoginUser;
import com.mighty.kora.config.auth.dto.SessionUser;
import com.mighty.kora.dto.user.*;
import com.mighty.kora.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/login")
    public Long login(@RequestBody UserLoginRequestDto requestDto) {

        return userService.login(requestDto);
    }

    @PostMapping("/api/signup/user")
    public Long save(@RequestBody UserSaveRequestDto requestDto) {
        return userService.save(requestDto);
    }

    @PostMapping("/api/signup/user_oauth")
    public Long save(@RequestBody UserOauthSaveRequestDto requestDto, @LoginUser SessionUser user) {
        return userService.oauthUpdate(user.getEmail(), requestDto);
    }

    @PostMapping("/api/signup/userEmailChk")
    public String emailDuplicateChk(@RequestBody UserEmailRequestDto requestDto) {
        return userService.emailDuplicateChk(requestDto);
    }

    @GetMapping("/api/signup/resendAuthMail")
    public Long resendAuthMail(@LoginUser SessionUser user) {
        return userService.resendAuthMail(user);
    }

    @PutMapping("/api/user/{id}")
    public Long update(@PathVariable Long id, @RequestBody UserUpdateRequestDto requestDto) {
        return userService.update(id, requestDto);
    }

    @GetMapping("/api/user/{id}")
    public UserResponseDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

}
