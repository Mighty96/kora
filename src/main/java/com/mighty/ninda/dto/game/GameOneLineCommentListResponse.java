package com.mighty.ninda.dto.game;

import com.mighty.ninda.domain.comment.OneLineComment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameOneLineCommentListResponse {

    private Long id;
    private Long userId;
    private String context;
    private String userName;
    private int reLike;
    private int reHate;

    @Builder
    public GameOneLineCommentListResponse(Long id, Long userId, String context, String userName, int reLike, int reHate) {
        this.id = id;
        this.userId = userId;
        this.context = context;
        this.userName = userName;
        this.reLike = reLike;
        this.reHate = reHate;
    }

    public static List<GameOneLineCommentListResponse> from(List<OneLineComment> oneLineComments) {
        List<GameOneLineCommentListResponse> response = new ArrayList<>();

        for (OneLineComment oneLineComment : oneLineComments) {
            response.add(GameOneLineCommentListResponse.builder()
                    .id(oneLineComment.getId())
                    .userId(oneLineComment.getUser().getId())
                    .context(oneLineComment.getContext())
                    .userName(oneLineComment.getUser().getNickname())
                    .reHate(oneLineComment.getReHate())
                    .reLike(oneLineComment.getReLike())
                    .build());
        }

        return response;
    }

}
