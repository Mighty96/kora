package com.mighty.ninda.dto.oneLineComment;

import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OneLineCommentSaveRequestDto {

    private User user;
    private String context;
    private Game game;
    private int parent_id;
    private int depth;
    private int orders;

    @Builder
    public OneLineCommentSaveRequestDto(User user, String context, Game game, int parent_id, int depth, int orders) {
        this.user = user;
        this.context = context;
        this.game = game;
        this.parent_id = parent_id;
        this.depth = depth;
        this.orders = orders;
    }
}
