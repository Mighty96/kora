package com.mighty.kora.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String password;
    private String nickname;

    @Builder
    public UserUpdateRequestDto(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }

}
