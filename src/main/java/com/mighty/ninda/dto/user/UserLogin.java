package com.mighty.ninda.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLogin {
    private String email;
    private String password;

    @Builder
    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }


}
