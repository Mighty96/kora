package com.mighty.ninda.domain.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Getter
public class PostQueryString {

    private final int page;

    private final int size;

    private final Optional<String> s_type;

    private final Optional<String> s_keyword;

    public PostQueryString(int page, int size, String s_type, String s_keyword) {
        this.page = page;
        this.size = size;
        this.s_type = Optional.ofNullable(s_type);
        this.s_keyword = Optional.ofNullable(s_keyword);
    }

    public String makeQueryString(int page) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParamIfPresent("s_type", s_type)
                .queryParamIfPresent("s_keyword", s_keyword)
                .build()
                .encode();

        return uriComponents.toUriString();
    }

}
