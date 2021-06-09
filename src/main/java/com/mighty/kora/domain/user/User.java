package com.mighty.kora.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Enumerated(EnumType.STRING)
    @Column
    private RegistrationId registrationId;

    @Column
    private String authKey;

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

    public void updateAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public void updateRoleToUser() {
        this.role = Role.USER;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}



