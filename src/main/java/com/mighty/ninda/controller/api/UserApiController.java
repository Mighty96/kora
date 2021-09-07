package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.dto.user.*;
import com.mighty.ninda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/users/signup/ninda")
    public void save(@RequestBody @Valid SaveUser requestDto) {
        userService.save(requestDto);
        log.info("saved");
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @PostMapping("/api/users/signup/oauth")
    public void save(@RequestBody @Valid SaveUserOauth requestDto, @LoginUser CurrentUser currentUser) {

        userService.oauthUpdate(currentUser, requestDto);
    }

    @PostMapping("/api/users/signup/emailChk")
    public String emailDuplicateChk(@RequestBody @Valid UserEmail requestDto) {
        return userService.emailDuplicateChk(requestDto);
    }

    @PostMapping("/api/users/nicknameChk")
    public String nicknameDuplicateChk(@RequestBody @Valid UserNickname requestDto) {
        return userService.nicknameDuplicateChk(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_GUEST')")
    @GetMapping("/api/users/resendAuthMail")
    public void resendAuthMail(@LoginUser CurrentUser currentUser) {
        userService.resendAuthMail(currentUser);
    }

    @PreAuthorize("hasAnyRole('ROLE_GUEST', 'ROLE_USER')")
    @PutMapping("/api/users")
    public void update(@LoginUser CurrentUser currentUser, @RequestBody @Valid UpdateUser requestDto) {

        userService.update(currentUser, requestDto);
    }

    @PreAuthorize("hasAnyRole('GUEST', 'USER')")
    @PostMapping("/api/users/updatePassword")
    public void updatePassword(@LoginUser CurrentUser currentUser, @RequestBody @Valid UpdateUserPassword requestDto) {
        userService.changePassword(currentUser, requestDto.getOldPassword(), requestDto.getNewPassword());
    }

    @PreAuthorize("hasAnyRole('GUEST', 'USER')")
    @PostMapping("/api/users/newPassword")
    public void sendNewPassword(@RequestBody @Valid UserEmail requestDto) {
        userService.sendNewPassword(requestDto.getEmail());
    }


}
