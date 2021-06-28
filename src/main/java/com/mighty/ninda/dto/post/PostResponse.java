package com.mighty.ninda.dto.post;

import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.domain.user.User;
import lombok.Getter;

@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private User user;

    public PostResponse(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user = entity.getUser();
    }
}
