package com.mighty.ninda.domain.game;

import com.mighty.ninda.domain.comment.OneLineComment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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

    private int reLike;
    private int reHate;
    private String imgUrl;
    private String pageUrl;
    private String price;
    private int viewCount;
    private LocalDate releasedDate;
    private String language;

    @Column(length= 100000)
    private String likeList;

    @Column(length = 100000)
    private String hateList;

    @OneToMany(mappedBy = "game")
    private List<OneLineComment> oneLineComments;

    @Builder
    public Game(String title, String description, String imgUrl, String pageUrl, String price, LocalDate releasedDate, int viewCount, int reLike, int reHate, String language, String likeList, String hateList) {
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
        this.pageUrl = pageUrl;
        this.price = price;
        this.releasedDate = releasedDate;
        this.viewCount = viewCount;
        this.reLike = reLike;
        this.reHate = reHate;
        this.language = language;
        this.likeList = likeList;
        this.hateList = hateList;
    }

    public void viewCountUp() {
        this.viewCount++;
    }

    public void reLikeUp() {
        this.reLike++;
    }

    public void reHateUp() {
        this.reHate++;
    }

    public void updateLikeList(String id) {

        if (!this.likeList.equals("")) {
            this.likeList +=  "," + id;
        } else {
            this.likeList += id;
        }
    }

    public void updateHateList(String id) {

        if (!this.hateList.equals("")) {
            this.hateList +=  "," + id;
        } else {
            this.hateList += id;
        }
    }
}
