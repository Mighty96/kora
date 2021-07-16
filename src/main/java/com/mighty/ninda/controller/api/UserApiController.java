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
    public Long save(@RequestBody SaveUser requestDto) {
        return userService.save(requestDto);
    }

    @PreAuthorize("hasRole('GUEST')")
    @PostMapping("/api/users/signup/oauth")
    public Long save(@RequestBody SaveUserOauth requestDto, @LoginUser SessionUser user) {
        log.info("oauth");
        return userService.oauthUpdate(user.getEmail(), requestDto);
    }

    @PostMapping("/api/users/signup/emailChk")
    public String emailDuplicateChk(@RequestBody UserEmail requestDto) {
        return userService.emailDuplicateChk(requestDto);
    }

    @PreAuthorize("hasRole('GUEST')")
    @GetMapping("/api/users/resendAuthMail")
    public Long resendAuthMail(@LoginUser SessionUser user) {
        return userService.resendAuthMail(user);
    }

    @PreAuthorize("hasAnyRole('GUEST', 'USER')")
    @PutMapping("/api/users")
    public Long update(@LoginUser SessionUser user, @RequestBody UpdateUser requestDto) {

        return userService.update(user.getId(), requestDto);
    }

    @PreAuthorize("hasAnyRole('GUEST', 'USER')")
    @PostMapping("/api/users/updatePassword")
    public Long updatePassword(@LoginUser SessionUser sessionUser, @RequestBody UpdateUserPassword requestDto) {
        return userService.changePassword(sessionUser.getId(), requestDto.getOldPassword(), requestDto.getNewPassword());
    }

    @PreAuthorize("hasAnyRole('GUEST', 'USER')")
    @PostMapping("/api/users/newPassword")
    public Long sendNewPassword(@RequestBody UserEmail requestDto) {
        return userService.sendNewPassword(requestDto.getEmail());
    }


}
