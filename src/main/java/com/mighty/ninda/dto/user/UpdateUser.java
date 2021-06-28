package com.mighty.ninda.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUser {

    private String nickname;
    private String introduction;

    @Builder
    public UpdateUser(String nickname, String introduction) {
        this.nickname = nickname;
        this.introduction = introduction;
    }

}
