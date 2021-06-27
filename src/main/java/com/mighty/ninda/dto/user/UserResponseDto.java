package com.mighty.ninda.dto.user;

import com.mighty.ninda.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final String introduction;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.nickname = entity.getNickname();
        this.introduction = entity.getIntroduction();
    }
}
