package com.mighty.ninda.dto.direct;

import com.mighty.ninda.domain.direct.Direct;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectListResponse {

    private Long id;
    private String title;
    private int viewCount;
    private int reLike;
    private String releasedDate;

    @Builder
    public DirectListResponse(Long id, String title, int viewCount, int reLike, LocalDate releasedDate) {
        this.id = id;
        this.title = title;
        this.viewCount = viewCount;
        this.reLike = reLike;
        this.releasedDate = Integer.toString(releasedDate.getYear()).substring(2) + "." + releasedDate.getMonthValue() + "." + releasedDate.getDayOfMonth();
    }

    public static List<DirectListResponse> of(List<Direct> directs) {

        List<DirectListResponse> response = new ArrayList<>();
        for (Direct direct : directs) {
            response.add(DirectListResponse.builder()
                    .title(direct.getTitle())
                    .id(direct.getId())
                    .releasedDate(direct.getReleasedDate())
                    .viewCount(direct.getViewCount())
                    .reLike(direct.getReLike())
                    .build());
        }

        return response;
    }

}
