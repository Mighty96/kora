package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.dto.user.*;
import com.mighty.ninda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/login")
    public Long login(@RequestBody UserLogin requestDto) {

        return userService.login(requestDto);
    }

    @PostMapping("/api/user/signup/ninda")
    public Long save(@RequestBody SaveUser requestDto) {
        return userService.save(requestDto);
    }

    @PostMapping("/api/user/signup/oauth")
    public Long save(@RequestBody SaveUserOauth requestDto, @LoginUser SessionUser user) {
        return userService.oauthUpdate(user.getEmail(), requestDto);
    }

    @PostMapping("/api/user/signup/emailChk")
    public String emailDuplicateChk(@RequestBody UserEmail requestDto) {
        return userService.emailDuplicateChk(requestDto);
    }

    @GetMapping("/api/user/resendAuthMail")
    public Long resendAuthMail(@LoginUser SessionUser user) {
        return userService.resendAuthMail(user);
    }

    @PutMapping("/api/user/update")
    public Long update(@LoginUser SessionUser user, @RequestBody UpdateUser requestDto) {

        return userService.update(user.getId(), requestDto);
    }

    @PostMapping("/api/user/updatePassword")
    public Long updatePassword(@RequestBody UserEmail requestDto) {
        return userService.sendNewPassword(requestDto.getEmail());
    }

    @PostMapping("/api/user/newPassword")
    public Long sendNewPassword(@RequestBody UserEmail requestDto) {
        return userService.sendNewPassword(requestDto.getEmail());
    }

    @GetMapping("/api/user/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

}
