package com.mighty.ninda.dto.post;

import com.mighty.ninda.domain.comment.Comment;
import com.mighty.ninda.dto.game.GameOneLineCommentListResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentListResponse {

    private Long id;
    private Long userId;
    private String context;
    private String userName;
    private int reLike;
    private int reHate;

    @Builder
    public PostCommentListResponse(Long id, Long userId, String context, String userName, int reLike, int reHate) {
        this.id = id;
        this.userId = userId;
        this.context = context;
        this.userName = userName;
        this.reLike = reLike;
        this.reHate = reHate;
    }

    public static List<PostCommentListResponse> of(List<Comment> comments) {
        List<PostCommentListResponse> response = new ArrayList<>();

        for (Comment comment : comments) {
            response.add(PostCommentListResponse.builder()
                    .id(comment.getId())
                    .userId(comment.getUser().getId())
                    .context(comment.getContext())
                    .userName(comment.getUser().getNickname())
                    .reHate(comment.getReHate())
                    .reLike(comment.getReLike())
                    .build());
        }

        return response;
    }

}
