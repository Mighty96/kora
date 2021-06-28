package com.mighty.ninda.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserEmail {

    private String email;

    @Builder
    public UserEmail(String email) {
        this.email = email;
    }
}
