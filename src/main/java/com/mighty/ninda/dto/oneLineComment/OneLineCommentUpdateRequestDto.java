package com.mighty.ninda.dto.oneLineComment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OneLineCommentUpdateRequestDto {

    private String context;

    @Builder
    public OneLineCommentUpdateRequestDto(String context) {
        this.context = context;
    }
}
