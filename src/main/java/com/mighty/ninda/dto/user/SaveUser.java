package com.mighty.ninda.dto.user;

import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveUser {

    private String email;
    private String password;
    private String nickname;
    private String picture;
    private Role role;
    private RegistrationId registrationId;
    private String authKey;

    @Builder
    public SaveUser(String email, String password, String nickname, RegistrationId registrationId, String authKey) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = Role.GUEST;
        this.registrationId = registrationId;
        this.authKey = authKey;
    }

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
