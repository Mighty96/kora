package com.mighty.kora.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String password;
    private String nickname;
    private String picture;

    @Builder
    public UserUpdateRequestDto(String password, String nickname, String picture) {
        this.password = password;
        this.nickname = nickname;
        this.picture = picture;
    }

}
