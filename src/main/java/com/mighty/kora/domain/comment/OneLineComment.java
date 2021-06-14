package com.mighty.kora.domain.comment;

import com.mighty.kora.domain.BaseTimeEntity;
import com.mighty.kora.domain.game.Game;
import com.mighty.kora.domain.user.User;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 200, nullable = false)
    private String context;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private int score;
    private int parent_id;
    private int depth;
    private int orders;
    private int recommended_up;
    private int recommended_down;

}
