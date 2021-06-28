package com.mighty.ninda.dto.index;

import com.mighty.ninda.domain.comment.OneLineComment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndexOneLineCommentListResponse {

    private String context;
    private String gameTitle;
    private Long gameId;

    @Builder
    public IndexOneLineCommentListResponse(String context, String gameTitle, Long gameId) {
        this.context = context;
        this.gameTitle = gameTitle;
        this.gameId = gameId;
    }

    public static List<IndexOneLineCommentListResponse> from(List<OneLineComment> oneLineComments) {
        List<IndexOneLineCommentListResponse> response = new ArrayList<>();

        for (OneLineComment oneLineComment : oneLineComments) {
            response.add(IndexOneLineCommentListResponse.builder()
            .gameTitle(oneLineComment.getGame().getTitle())
            .gameId(oneLineComment.getGame().getId())
            .context(oneLineComment.getContext())
            .build());
        }

        return response;
    }
}
