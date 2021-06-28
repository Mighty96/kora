package com.mighty.ninda.dto.index;

import com.mighty.ninda.domain.game.Game;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndexNewGameListResponse {

    private Long id;
    private String title;
    private String imgUrl;
    private String releasedDate;

    @Builder
    public IndexNewGameListResponse(Long id, String title, String imgUrl, LocalDate releasedDate) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.releasedDate = releasedDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

    }

    public static List<IndexNewGameListResponse> of(List<Game> games) {
        List<IndexNewGameListResponse> response = new ArrayList<>();

        for (Game game : games) {
            response.add(IndexNewGameListResponse.builder()
                    .id(game.getId())
                    .title(game.getTitle())
                    .imgUrl(game.getImgUrl())
                    .releasedDate(game.getReleasedDate())
                    .build());
        }

        return response;
    }

}
