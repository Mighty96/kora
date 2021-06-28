package com.mighty.ninda.dto.game;

import com.mighty.ninda.domain.game.Game;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameResponse {

    private Long id;
    private String title;
    private String description;
    private String pageUrl;
    private String imgUrl;
    private String releasedDate;
    private String price;
    private String language;
    private int reLike;
    private int reHate;

    @Builder
    public GameResponse(Long id, String title, String description, String pageUrl, String imgUrl, LocalDate releasedDate, String price, String language, int reLike, int reHate) {
        this.id = id;
        this.title = title;
        this.description = description.replace("\n", "<br>");
        this.pageUrl = pageUrl;
        this.imgUrl = imgUrl;
        this.releasedDate = releasedDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.price = price;
        this.language = language;
        this.reLike = reLike;
        this.reHate = reHate;
    }

    public static GameResponse from(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .title(game.getTitle())
                .description(game.getDescription())
                .pageUrl(game.getPageUrl())
                .imgUrl(game.getImgUrl())
                .releasedDate(game.getReleasedDate())
                .price(game.getPrice())
                .language(game.getLanguage())
                .reLike(game.getReLike())
                .reHate(game.getReHate())
                .build();
    }
}
