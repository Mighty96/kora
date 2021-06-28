package com.mighty.ninda.dto.post;

import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private User user;
}
