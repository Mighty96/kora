package com.mighty.ninda.dto.impression;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImpressionUpdateRequestDto {

    private String context;

    @Builder
    public ImpressionUpdateRequestDto(String context) {
        this.context = context;
    }
}
