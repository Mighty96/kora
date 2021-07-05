package com.mighty.ninda.dto.post;

import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

    private Long id;
    private String title;
    private String context;
    private String createdDate;
    private Long userId;
    private String userName;

    @Builder
    public PostResponse(Long id, String title, String context, LocalDateTime createdDate, Long userId, String userName) {
        this.id = id;
        this.title = title;
        this.context = context;
        this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.userId = userId;
        this.userName = userName;
    }

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .context(post.getContext())
                .createdDate(post.getCreatedDate())
                .userId(post.getUser().getId())
                .userName(post.getUser().getNickname())
                .build();
    }
}
