package com.mighty.ninda.dto.direct;

import com.mighty.ninda.domain.direct.Direct;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectResponse {

    private Long id;
    private String japanUrl;
    private String americaUrl;
    private int reLike;
    private int reHate;

    @Builder
    public DirectResponse(Long id, String japanUrl, String americaUrl, int reLike, int reHate) {
        this.id = id;
        this.japanUrl = japanUrl;
        this.americaUrl = americaUrl;
        this.reLike = reLike;
        this.reHate = reHate;
    }

    public static DirectResponse from(Direct direct) {
        return DirectResponse.builder()
                .id(direct.getId())
                .japanUrl(direct.getJapanUrl())
                .americaUrl(direct.getAmericaUrl())
                .reLike(direct.getReLike())
                .reHate(direct.getReHate())
                .build();
    }
}
