package com.mighty.ninda.dto.direct;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveDirect {

    private String title;
    private String japanUrl;
    private String americaUrl;

}
