package com.mighty.ninda.dto.direct;

import com.mighty.ninda.domain.direct.Direct;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectListResponse {

    private Long id;
    private String title;

    @Builder
    public DirectListResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static List<DirectListResponse> from(List<Direct> directs) {

        List<DirectListResponse> response = new ArrayList<>();
        for (Direct direct : directs) {
            response.add(DirectListResponse.builder()
                    .title(direct.getTitle())
                    .id(direct.getId())
                    .build());
        }

        return response;
    }

}
