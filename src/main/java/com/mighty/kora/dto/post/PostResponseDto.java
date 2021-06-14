package com.mighty.kora.dto.post;

import com.mighty.kora.domain.post.Post;
import com.mighty.kora.domain.user.User;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private User user;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user = entity.getUser();
    }
}
