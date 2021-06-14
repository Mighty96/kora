package com.mighty.kora.dto.user;

import com.mighty.kora.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.nickname = entity.getNickname();
    }
}
