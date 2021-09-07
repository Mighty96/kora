package com.mighty.ninda.domain.comment;

import com.mighty.ninda.domain.direct.Direct;
import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class Impression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "impression_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(length = 200, nullable = false)
    private String context;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direct_id")
    private Direct direct;

    private int reLike;
    private int reHate;

    @Column(length= 100000)
    private String likeList;

    @Column(length = 100000)
    private String hateList;


    @Builder
    public Impression(User user, String context, Direct direct, int reLike, int reHate, String likeList, String hateList) {
        this.user = user;
        this.context = context;
        this.direct = direct;
        this.reLike = reLike;
        this.reHate = reHate;
        this.likeList = likeList;
        this.hateList = hateList;
    }

    public void update(String context) {
        this.context = context;
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
