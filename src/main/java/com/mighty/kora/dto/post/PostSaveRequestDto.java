package com.mighty.kora.dto.post;

import com.mighty.kora.domain.Language;
import com.mighty.kora.domain.post.Post;
import com.mighty.kora.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private User user;
    private Language myLanguage;
    private Language yourLanguage;

    @Builder
    public PostSaveRequestDto(String title, String content, User user, Language myLanguage, Language yourLanguage) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.myLanguage = myLanguage;
        this.yourLanguage = yourLanguage;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .myLanguage(myLanguage)
                .yourLanguage(yourLanguage)
                .build();
    }
}
