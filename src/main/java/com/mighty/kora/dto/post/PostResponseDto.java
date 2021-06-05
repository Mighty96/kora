package com.mighty.kora.dto.post;

import com.mighty.kora.domain.Language;
import com.mighty.kora.domain.post.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private Language myLanguage;
    private Language yourLanguage;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.myLanguage = entity.getMyLanguage();
        this.yourLanguage = entity.getYourLanguage();
    }
}
