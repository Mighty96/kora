package com.mighty.ninda.dto.user;

import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveUser {

    private String email;
    private String password;
    private String nickname;
    private Role role;
    private RegistrationId registrationId;
    private String authKey;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(role)
                .registrationId(registrationId)
                .authKey(authKey)
                .build();

    }
}
