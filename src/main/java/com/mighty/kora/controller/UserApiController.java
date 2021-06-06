package com.mighty.kora.controller;

import com.mighty.kora.dto.user.UserEmailRequestDto;
import com.mighty.kora.dto.user.UserResponseDto;
import com.mighty.kora.dto.user.UserSaveRequestDto;
import com.mighty.kora.dto.user.UserUpdateRequestDto;
import com.mighty.kora.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/user")
    public Long save(@RequestBody UserSaveRequestDto requestDto) {
        return userService.save(requestDto);
    }

    @ResponseBody
    @PostMapping("/api/userEmailChk")
    public String emailDuplicateChk(@RequestBody UserEmailRequestDto requestDto) {
        return userService.emailDuplicateChk(requestDto);
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
