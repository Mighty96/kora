package com.mighty.kora.domain.user;

import com.mighty.kora.domain.comment.Comment;
import com.mighty.kora.domain.comment.OneLineComment;
import com.mighty.kora.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    public User(String email, String password, String nickname, String picture, Role role, RegistrationId registrationId, String authKey) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.picture = picture;
        this.role = role;
        this.registrationId = registrationId;
        this.authKey = authKey;
    }


    public void update(String password, String nickname, String picture) {
        this.password = password;
        this.picture = picture;
        this.nickname = nickname;
    }

    public void encodePassword(String password) {
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



