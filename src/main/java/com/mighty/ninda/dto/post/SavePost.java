package com.mighty.ninda.dto.post;

import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavePost {
    private String title;
    private String content;
    private User user;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
