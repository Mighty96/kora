package com.mighty.ninda.domain.comment;

import com.mighty.ninda.domain.BaseTimeEntity;
import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class OneLineComment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "one_line_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 200, nullable = false)
    private String context;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    private int recommended_up;
    private int recommended_down;

    @Builder
    public OneLineComment(User user, String context, Game game, int recommended_up, int recommended_down) {
        this.user = user;
        this.context = context;
        this.game = game;
        this.recommended_up = recommended_up;
        this.recommended_down = recommended_down;
    }

    public void update(String context) {
        this.context = context;
    }

}
