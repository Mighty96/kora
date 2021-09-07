package com.mighty.ninda.dto.user;

import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveUser {

    @Email
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}$")
    private String password;

    @Size(min=2, max=12)
    private String nickname;

    @NotNull
    private Role role;

    @NotNull
    private RegistrationId registrationId;

    @NotNull
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
