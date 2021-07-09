package com.mighty.ninda.dto.user;

import com.mighty.ninda.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPostListResponse {

    private Long postId;
    private String postTitle;
    private String context;
    private String createdDate;

    @Builder
    public UserPostListResponse(Long postId, String postTitle, String context, LocalDateTime createdDate) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.context = context;
        this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));


    }

    public static UserPostListResponse of(Post post) {
        return UserPostListResponse.builder()
                .postId(post.getId())
                .postTitle(post.getTitle())
                .context(post.getContext())
                .createdDate(post.getCreatedDate())
                .build();
    }
}
