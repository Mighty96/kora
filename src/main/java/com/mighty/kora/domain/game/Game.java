package com.mighty.kora.domain.game;

import com.mighty.kora.domain.comment.OneLineComment;
import lombok.Builder;
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

    @Column(unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private float score;

    private int voteCount;

    private String imgUrl;

    private String pageUrl;

    private String price;

    private int viewCount;

    private String releasedDate;

    @OneToMany(mappedBy = "game")
    private List<OneLineComment> oneLineComments;

    @Builder
    public Game(String title, String description, String imgUrl, String pageUrl, String price, String releasedDate, int viewCount, int voteCount, float score) {
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
        this.pageUrl = pageUrl;
        this.price = price;
        this.releasedDate = releasedDate;
        this.viewCount = viewCount;
        this.voteCount = voteCount;
        this.score = score;
    }

}
