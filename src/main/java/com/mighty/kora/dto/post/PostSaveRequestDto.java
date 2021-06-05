package com.mighty.kora.dto.post;

import com.mighty.kora.domain.Language;
import com.mighty.kora.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private Language myLanguage;
    private Language yourLanguage;

    @Builder
    public PostSaveRequestDto(String title, String content, String author, Language myLanguage, Language yourLanguage) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.myLanguage = myLanguage;
        this.yourLanguage = yourLanguage;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .myLanguage(myLanguage)
                .yourLanguage(yourLanguage)
                .build();
    }
}
