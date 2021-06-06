package com.mighty.kora.domain.post;

import com.mighty.kora.domain.BaseTimeEntity;
import com.mighty.kora.domain.Language;
import com.mighty.kora.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    private Language myLanguage;

    @Enumerated
    private Language yourLanguage;

    @Builder
    public Post(String title, String content, User user, Language myLanguage, Language yourLanguage) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.myLanguage = myLanguage;
        this.yourLanguage = yourLanguage;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
