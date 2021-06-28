package com.mighty.ninda.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatePost {
    private String title;
    private String content;

    @Builder
    public UpdatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
