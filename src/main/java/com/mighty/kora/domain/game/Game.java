package com.mighty.kora.domain.game;

import com.mighty.kora.domain.comment.OneLineComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Game {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    private String title;

    private String content;

    private float score;

    private String img;

    private int price;

    private String releaseDate;

    @OneToMany(mappedBy = "game")
    private List<OneLineComment> oneLineComments;

}
