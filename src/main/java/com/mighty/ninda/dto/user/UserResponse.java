package com.mighty.ninda.dto.user;

import com.mighty.ninda.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {

    private String nickname;
    private String introduction;
    private String registrationDate;

    @Builder
    public UserResponse(String nickname, String introduction, LocalDate registrationDate) {
        this.nickname = nickname;
        this.introduction = introduction;
        this.registrationDate = registrationDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    }

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .nickname(user.getNickname())
                .introduction(user.getIntroduction())
                .registrationDate(user.getRegistrationDate())
                .build();
    }
}
