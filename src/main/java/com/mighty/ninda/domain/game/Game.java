package com.mighty.ninda.domain.game;

import com.mighty.ninda.domain.comment.OneLineComment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class Game {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String title;

    @NotNull
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
    private String onSale;
    private LocalDate startSale;
    private LocalDate endSale;
    private String salePrice;

    @Column(length= 100000)
    private String likeList;

    @Column(length = 100000)
    private String hateList;

    @OneToMany(mappedBy = "game")
    private List<OneLineComment> oneLineComments;

    @Builder
    public Game(String title, String description, String imgUrl, String pageUrl, String price, LocalDate releasedDate, String language) {
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
        this.pageUrl = pageUrl;
        this.price = price;
        this.releasedDate = releasedDate;
        this.language = language;
        this.salePrice = "";
        this.viewCount = 0;
        this.reLike = 0;
        this.reHate = 0;
        this.likeList = "";
        this.hateList = "";
        this.onSale = "";
        this.startSale = null;
        this.endSale = null;
    }

    public void updateGame(String description, String imgUrl, String price, String language) {
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        this.language = language;
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

    public void onSale(String saleDate, String salePrice) {
        this.salePrice = salePrice;
        Pattern pattern = Pattern.compile("([0-2][0-9]{3})\\.([0-9]+)\\.([0-9]+)");
        Matcher matcher = pattern.matcher(saleDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.M.d");
        if (matcher.find()) {
            this.startSale = LocalDate.parse(matcher.group(), formatter);
        }
        if (matcher.find()) {
            this.endSale = LocalDate.parse(matcher.group(), formatter);
        }
        this.onSale = "on";
    }

    public void offSale() {
        this.salePrice = "";
        this.onSale = "";
        this.startSale = null;
        this.endSale = null;
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
