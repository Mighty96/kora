package com.mighty.kora.dto.user;

import com.mighty.kora.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String email;
    private String password;
    private String name;
    private String birthday;
    private String nickname;

    @Builder
    public UserSaveRequestDto(String email, String password, String name, String birthday, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.nickname = nickname;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .birthday(birthday)
                .nickname(nickname)
                .build();

    }
}
