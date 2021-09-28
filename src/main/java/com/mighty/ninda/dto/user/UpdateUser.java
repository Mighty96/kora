package com.mighty.ninda.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateUser {

    @Size(min = 2, max = 12)
    private String nickname;

    @Size(max = 200)
    private String introduction;

    public UpdateUser(String nickname, String introduction) {
        this.nickname = nickname;
        this.introduction = introduction;
    }
}
