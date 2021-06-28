package com.mighty.ninda.dto.impression;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveImpression {

    private String context;
    private Long directId;

    @Builder
    public SaveImpression(String context, Long directId) {
        this.context = context;
        this.directId = directId;
    }
}
