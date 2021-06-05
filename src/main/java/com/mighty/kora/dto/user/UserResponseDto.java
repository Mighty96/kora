package com.mighty.kora.dto.user;

import com.mighty.kora.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String birthday;
    private String nickname;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.name = entity.getName();
        this.birthday = entity.getBirthday();
        this.nickname = entity.getNickname();
    }
}
