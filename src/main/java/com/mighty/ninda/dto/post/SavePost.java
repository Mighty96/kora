package com.mighty.ninda.dto.post;

import com.mighty.ninda.domain.post.Board;
import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavePost {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    private String context;

    @NotNull
    private String board;

    @Builder
    public SavePost(String title, String context, String board) {
        this.title = title;
        this.context = context;
        this.board = board;
    }

    public Post toEntity(User user) {
        return Post.builder()
                .board(board)
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
