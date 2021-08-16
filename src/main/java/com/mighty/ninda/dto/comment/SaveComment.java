package com.mighty.ninda.dto.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveComment {

    private String context;
    private Long postId;
    private Long commentId;
}
