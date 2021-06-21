package com.mighty.ninda.dto.oneLineComment;

import com.mighty.ninda.domain.game.Game;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OneLineCommentSaveRequestDto {

    private String context;
    private Long gameId;

    @Builder
    public OneLineCommentSaveRequestDto(String context, Long gameId) {
        this.context = context;
        this.gameId = gameId;
    }
}
