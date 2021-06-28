package com.mighty.ninda.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserPassword {

    private String password;

    @Builder
    public UpdateUserPassword(String password) {
        this.password = password;
    }
}
