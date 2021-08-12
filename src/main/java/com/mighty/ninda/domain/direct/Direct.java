package com.mighty.ninda.domain.direct;

import com.mighty.ninda.domain.comment.Impression;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class Direct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "direct_id")
    private Long id;

    private String title;

    private LocalDate releasedDate;

    private String koreaUrl;
    private String japanUrl;
    private String americaUrl;

    @OneToMany(mappedBy = "direct")
    private List<Impression> impression;

    private int reLike;
    private int reHate;

    private int viewCount;

    @Column(length= 100000)
    private String likeList;

    @Column(length = 100000)
    private String hateList;

    @Builder
    public Direct(String title, LocalDate releasedDate, String koreaUrl, String japanUrl, String americaUrl, int reLike, int reHate, int viewCount, String likeList, String hateList) {
        this.title = title;
        this.releasedDate = releasedDate;
        this.koreaUrl = koreaUrl;
        this.japanUrl = japanUrl;
        this.americaUrl = americaUrl;
        this.impression = new ArrayList<>();
        this.reHate = reHate;
        this.reLike = reLike;
        this.viewCount = viewCount;
        this.likeList = likeList;
        this.hateList = hateList;
    }

    public void reLikeUp() {
        this.reLike++;
    }

    public void reHateUp() {
        this.reHate++;
    }

    public void viewCountUp() {
        this.viewCount++;
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
