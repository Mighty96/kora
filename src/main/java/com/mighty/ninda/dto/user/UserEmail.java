package com.mighty.ninda.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEmail {

    @Email
    private String email;

    @Builder
    public UserEmail(String email) {
        this.email = email;
    }
}
