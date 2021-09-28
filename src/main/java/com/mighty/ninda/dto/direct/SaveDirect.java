package com.mighty.ninda.dto.direct;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveDirect {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    private String releasedDate;
    private String koreaUrl;
    private String japanUrl;
    private String americaUrl;

    public SaveDirect(String title, String releasedDate, String koreaUrl, String japanUrl, String americaUrl) {
        this.title = title;
        this.releasedDate = releasedDate;
        this.koreaUrl = koreaUrl;
        this.japanUrl = japanUrl;
        this.americaUrl = americaUrl;
    }

}
