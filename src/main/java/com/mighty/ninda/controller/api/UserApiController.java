package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.dto.user.*;
import com.mighty.ninda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserApiController {

    private final UserService userService;


    @PostMapping("/api/users/signup/ninda")
    public void save(@RequestBody SaveUser requestDto) {
        userService.save(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @PostMapping("/api/users/signup/oauth")
    public void save(@RequestBody SaveUserOauth requestDto, @LoginUser SessionUser sessionUser) {

        userService.oauthUpdate(sessionUser, requestDto);
    }

    @PostMapping("/api/users/signup/emailChk")
    public void emailDuplicateChk(@RequestBody UserEmail requestDto) {
        userService.emailDuplicateChk(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @GetMapping("/api/users/resendAuthMail")
    public void resendAuthMail(@LoginUser SessionUser sessionUser) {
        userService.resendAuthMail(sessionUser);
    }

    @PreAuthorize("hasAnyRole('ROLE_GUEST', 'ROLE_USER')")
    @PutMapping("/api/users")
    public void update(@LoginUser SessionUser sessionUser, @RequestBody UpdateUser requestDto) {

        userService.update(sessionUser, requestDto);
    }

    @PreAuthorize("hasAnyRole('GUEST', 'USER')")
    @PostMapping("/api/users/updatePassword")
    public void updatePassword(@LoginUser SessionUser sessionUser, @RequestBody UpdateUserPassword requestDto) {
        userService.changePassword(sessionUser, requestDto.getOldPassword(), requestDto.getNewPassword());
    }

    @PreAuthorize("hasAnyRole('GUEST', 'USER')")
    @PostMapping("/api/users/newPassword")
    public void sendNewPassword(@RequestBody UserEmail requestDto) {
        userService.sendNewPassword(requestDto.getEmail());
    }


}
