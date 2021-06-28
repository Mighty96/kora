package com.mighty.ninda.dto.user;

import com.mighty.ninda.domain.comment.OneLineComment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOneLineCommentListResponse {

    private Long gameId;
    private String gameTitle;
    private Long commentId;
    private String context;
    private String createdDate;

    @Builder
    public UserOneLineCommentListResponse(Long gameId, String gameTitle, Long commentId, String context, LocalDateTime createdDate) {
        this.gameId = gameId;
        this.gameTitle = gameTitle;
        this.commentId = commentId;
        this.context = context;
        this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));


    }

    public static List<UserOneLineCommentListResponse> from(List<OneLineComment> oneLineComments) {
        List<UserOneLineCommentListResponse> list = new ArrayList<>();

        for (OneLineComment oneLineComment : oneLineComments) {
            list.add(UserOneLineCommentListResponse.builder()
                    .gameId(oneLineComment.getGame().getId())
                    .gameTitle(oneLineComment.getGame().getTitle())
                    .commentId(oneLineComment.getId())
                    .context(oneLineComment.getContext())
                    .createdDate(oneLineComment.getCreatedDate())
                    .build());
        }

        return list;
    }
}
