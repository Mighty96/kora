package com.mighty.ninda.config.auth.dto;

import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private Long id;
    private String email;
    private String nickname;
    private Role role;
    private RegistrationId registrationId;

    public SessionUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.registrationId = user.getRegistrationId();
    }
}
