package com.mighty.kora.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바르지 않은 이메일입니다.")
    @Column
    private String email;

    @Size(min = 8, max = 15, message = "비밀번호는 8~15자로 이루어져야 합니다.")
    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String birthday;

    @Column
    private String nickname;

    @Builder
    public User(String email, String password, String name, String birthday, String nickname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.nickname = nickname;
    }

    public void update(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }

}



