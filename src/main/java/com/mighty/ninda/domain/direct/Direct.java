package com.mighty.ninda.domain.direct;

import com.mighty.ninda.domain.comment.Impression;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Direct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "direct_id")
    private Long id;

    private String title;

    private String japanUrl;
    private String americaUrl;

    @OneToMany(mappedBy = "direct")
    private List<Impression> impression;

    private int reLike;
    private int reHate;

    @Column(length= 100000)
    private String likeList;

    @Column(length = 100000)
    private String hateList;

    @Builder
    public Direct(String title, String japanUrl, String americaUrl, int reLike, int reHate, String likeList, String hateList) {
        this.title = title;
        this.japanUrl = japanUrl;
        this.americaUrl = americaUrl;
        this.reHate = reHate;
        this. reLike = reLike;
        this.likeList = likeList;
        this.hateList = hateList;
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
