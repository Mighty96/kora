package com.mighty.ninda.dto.direct;

import com.mighty.ninda.domain.comment.Impression;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectImpressionListResponse {

    private Long id;
    private Long userId;
    private String context;
    private String userName;
    private int reLike;
    private int reHate;


    @Builder
    public DirectImpressionListResponse(Long id, Long userId, String context, String userName, int reLike, int reHate) {
        this.id = id;
        this.userId = userId;
        this.context = context;
        this.userName = userName;
        this.reLike = reLike;
        this.reHate = reHate;
    }

    public static List<DirectImpressionListResponse> of(List<Impression> impressions) {
        List<DirectImpressionListResponse> response = new ArrayList<>();

        for (Impression impression : impressions) {
            response.add(DirectImpressionListResponse.builder()
                    .id(impression.getId())
                    .userId(impression.getUser().getId())
                    .context(impression.getContext())
                    .userName(impression.getUser().getNickname())
                    .reLike(impression.getReLike())
                    .reHate(impression.getReHate())
                    .build());
        }

        return response;
    }

}
