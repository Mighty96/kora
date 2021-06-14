package com.mighty.kora.dto.post;

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

    @Builder
    public PostSaveRequestDto(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
