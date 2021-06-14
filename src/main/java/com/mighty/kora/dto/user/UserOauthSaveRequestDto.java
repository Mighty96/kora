package com.mighty.kora.dto.user;

import com.mighty.kora.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserOauthSaveRequestDto {

    private String nickname;

    @Builder
    public UserOauthSaveRequestDto(String nickname) {
        this.nickname = nickname;
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .build();

    }
}
