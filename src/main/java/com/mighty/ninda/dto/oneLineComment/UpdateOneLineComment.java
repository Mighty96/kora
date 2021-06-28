package com.mighty.ninda.dto.oneLineComment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateOneLineComment {

    private String context;

    @Builder
    public UpdateOneLineComment(String context) {
        this.context = context;
    }
}
