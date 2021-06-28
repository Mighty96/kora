package com.mighty.ninda.dto.oneLineComment;

import com.mighty.ninda.domain.game.Game;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveOneLineComment {

    private String context;
    private Long gameId;
}
