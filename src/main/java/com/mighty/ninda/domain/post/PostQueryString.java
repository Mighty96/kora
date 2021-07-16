package com.mighty.ninda.domain.post;

import lombok.Getter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Getter
public class PostQueryString {

    private final int page;

    private final Optional<String> s_type;

    private final Optional<String> s_keyword;

    private final Optional<String> board;

    public PostQueryString(int page, String s_type, String s_keyword, String board) {
        this.page = page;
        this.s_type = Optional.ofNullable(s_type);
        this.s_keyword = Optional.ofNullable(s_keyword);
        this.board = Optional.ofNullable(board);
    }

    public String makeQueryString(int page) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParamIfPresent("s_type", s_type)
                .queryParamIfPresent("s_keyword", s_keyword)
                .queryParamIfPresent("board", board)
                .build()
                .encode();


        return uriComponents.toUriString();
    }

    public String changeBoard(String board) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParamIfPresent("s_type", s_type)
                .queryParamIfPresent("s_keyword", s_keyword)
                .queryParam("board", board)
                .build()
                .encode();


        return uriComponents.toUriString();
    }

}
