package com.mighty.kora.config.auth.dto;

import com.mighty.kora.domain.user.RegistrationId;
import com.mighty.kora.domain.user.Role;
import com.mighty.kora.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String email;
    private String nickname;
    private Role role;
    private RegistrationId registrationId;

    public SessionUser(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.registrationId = user.getRegistrationId();
    }
}
