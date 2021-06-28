package com.mighty.ninda.domain.user;

import com.mighty.ninda.domain.comment.Comment;
import com.mighty.ninda.domain.comment.OneLineComment;
import com.mighty.ninda.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String nickname;
    private String picture;
    private String introduction;
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private RegistrationId registrationId;

    private String authKey;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<OneLineComment> oneLineComments;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @Builder
    public User(String email, String password, String nickname, String introduction, Role role, RegistrationId registrationId, String authKey, LocalDate registrationDate) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.introduction = introduction;
        this.role = role;
        this.registrationId = registrationId;
        this.authKey = authKey;
        this.registrationDate = registrationDate;
    }

    public void update(String nickname, String introduction) {
        this.nickname = nickname;
        this.introduction = introduction;
    }

    public void updatePassword(String password) {
        this.password = password;
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