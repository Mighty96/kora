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
    private String context;
    private Long boardNo;

    public Post toEntity(User user) {
        return Post.builder()
                .boardNo(boardNo)
                .title(title)
                .context(context)
                .user(user)
                .likeList("")
                .hateList("")
                .reHate(0)
                .reLike(0)
                .viewCount(0)
                .build();
    }
}
