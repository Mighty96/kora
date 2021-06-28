package com.mighty.ninda.dto.direct;

import com.mighty.ninda.domain.direct.Direct;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DirectListResponse {

    private Long id;
    private String title;

    @Builder
    public DirectListResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public DirectListResponse from(Direct direct) {
        return DirectListResponse.builder()
                .id(direct.getId())
                .title(direct.getTitle())
                .build();
    }

}
