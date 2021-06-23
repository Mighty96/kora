package com.mighty.ninda.dto.impression;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImpressionSaveRequestDto {

    private String context;
    private Long directId;

    @Builder
    public ImpressionSaveRequestDto(String context, Long directId) {
        this.context = context;
        this.directId = directId;
    }
}
