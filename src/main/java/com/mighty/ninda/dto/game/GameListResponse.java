package com.mighty.ninda.dto.game;

import com.mighty.ninda.domain.game.Game;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameListResponse {

    private Long id;
    private String title;
    private String onSale;
    private String imgUrl;

    @Builder
    public GameListResponse(Long id, String title, String onSale, String imgUrl) {
        this.id = id;
        this.title = title;
        this.onSale = onSale;
        this.imgUrl = imgUrl;
    }

    public static GameListResponse of(Game game) {
        return GameListResponse.builder()
                .id(game.getId())
                .imgUrl(game.getImgUrl())
                .onSale(game.getOnSale())
                .title(game.getTitle())
                .build();
    }


}
