package com.mighty.ninda.dto.direct;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DirectSaveRequestDto {

    private String title;
    private String japanUrl;
    private String americaUrl;

    @Builder
    public DirectSaveRequestDto(String title, String japanUrl, String americaUrl) {
        this.title = title;
        this.japanUrl = japanUrl;
        this.americaUrl = americaUrl;
    }
}
