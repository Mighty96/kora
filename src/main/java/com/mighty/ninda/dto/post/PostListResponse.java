package com.mighty.ninda.dto.post;

import com.mighty.ninda.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListResponse {

    private Long id;
    private String title;
    private Long userId;
    private String userName;
    private String createdDate;
    private int viewCount;
    private int reLike;

    @Builder
    public PostListResponse(Long id, String title, Long userId, String userName, LocalDateTime created, int viewCount, int reLike) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.userName = userName;
        this.createdDate = changeDateFormat(created);
        this.viewCount = viewCount;
        this.reLike = reLike;
    }

    public static PostListResponse of(Post post) {
        return PostListResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .userId(post.getUser().getId())
                .userName(post.getUser().getNickname())
                .created(post.getCreatedDate())
                .viewCount(post.getViewCount())
                .reLike(post.getReLike())
                .build();
    }

    public String changeDateFormat(LocalDateTime created) {
        if (created.isBefore(LocalDate.now().atTime(0,0))) {
            return created.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        }

        return created.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

}
