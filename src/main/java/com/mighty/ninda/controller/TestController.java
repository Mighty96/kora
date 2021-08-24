package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @GetMapping("/test")
    public String test(@LoginUser CurrentUser user) {

        log.info(user.getEmail());
        log.info(user.getNickname());
        log.info(user.getRegistrationId().name());
        log.info(user.getRole().getKey());
        return null;
    }
}
