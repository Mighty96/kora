package com.mighty.ninda.dto.index;

import com.mighty.ninda.domain.game.Game;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndexHotGameListResponse {

    private Long id;
    private String title;

    @Builder
    public IndexHotGameListResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static List<IndexHotGameListResponse> of(List<Game> games) {
        List<IndexHotGameListResponse> response = new ArrayList<>();

        for (Game game : games) {
            response.add(IndexHotGameListResponse.builder()
                    .id(game.getId())
                    .title(game.getTitle())
                    .build());
        }

        return response;
    }
}
