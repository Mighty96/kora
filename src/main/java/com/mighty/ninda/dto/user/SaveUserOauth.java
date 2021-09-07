package com.mighty.ninda.dto.user;

import com.mighty.ninda.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveUserOauth {

    @Size(min = 2, max = 12)
    private String nickname;

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .build();

    }
}
