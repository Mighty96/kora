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
    @Column(nullable = false)
    private String email;

    @Column
    private String password;

    @Column
    private String familyName;

    @Column
    private String givenName;

    @Column
    private String birthday;

    @Column
    private String nickname;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String email, String password, String familyName, String givenName, String birthday, String nickname, String picture, Role role) {
        this.email = email;
        this.password = password;
        this.familyName = familyName;
        this.givenName = givenName;
        this.birthday = birthday;
        this.nickname = nickname;
        this.picture = picture;
        this.role = role;
    }

    public void update(String password, String nickname, String picture) {
        this.password = password;
        this.picture = picture;
        this.nickname = nickname;
    }

    public void oauthUpdate(String birthday, String nickname) {
        this.birthday = birthday;
        this.nickname = nickname;
        this.role = Role.USER;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}



