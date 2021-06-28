package com.mighty.ninda.dto.user;

import com.mighty.ninda.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {

    private String nickname;
    private String introduction;

    @Builder
    public UserResponse(String nickname, String introduction) {
        this.nickname = nickname;
        this.introduction = introduction;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .nickname(user.getNickname())
                .introduction(user.getIntroduction())
                .build();
    }
}
