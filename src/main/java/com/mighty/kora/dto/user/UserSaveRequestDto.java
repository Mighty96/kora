package com.mighty.kora.dto.user;

import com.mighty.kora.domain.user.RegistrationId;
import com.mighty.kora.domain.user.Role;
import com.mighty.kora.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String email;
    private String password;
    private String nickname;
    private String picture;
    private Role role;
    private RegistrationId registrationId;
    private String authKey;

    @Builder
    public UserSaveRequestDto(String email, String password, String nickname, String picture, RegistrationId registrationId, String authKey) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.picture = picture;
        this.role = Role.GUEST;
        this.registrationId = registrationId;
        this.authKey = authKey;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .picture(picture)
                .role(role)
                .registrationId(registrationId)
                .authKey(authKey)
                .build();

    }
}
