package com.mighty.ninda.dto.oneLineComment;

import com.mighty.ninda.domain.game.Game;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OneLineCommentSaveRequestDto {

    private String context;

    @Builder
    public OneLineCommentSaveRequestDto(String context) {
        this.context = context;
    }
}
