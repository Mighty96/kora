package com.mighty.ninda.dto.impression;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateImpression {

    private String context;

    @Builder
    public UpdateImpression(String context) {
        this.context = context;
    }
}
