package com.mighty.ninda.dto.post;

import com.mighty.ninda.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateResponse {

    private Long id;
    private String title;
    private String context;

    @Builder
    PostUpdateResponse(Long id, String title, String context) {
        this.id = id;
        this.title = title;
        this.context = context;
    }

    public static PostUpdateResponse of(Post post) {
        return PostUpdateResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .context(post.getContext())
                .build();
    }
}
