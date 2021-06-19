package com.mighty.ninda.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserEmailRequestDto {

    private String email;

    @Builder
    public UserEmailRequestDto(String email) {
        this.email = email;
    }
}
