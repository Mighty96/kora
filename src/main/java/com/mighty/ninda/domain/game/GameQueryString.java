package com.mighty.ninda.domain.game;

import lombok.Getter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Getter
public class GameQueryString {

    private final String q;

    private final List<String> sort;

    private final String list;

    private final int page;

    private final int size;

    public GameQueryString(String q, List<String> sort, String list, int page, int size) {
        this.q = q;
        this.sort = sort;
        this.list = list;
        this.page = page;
        this.size = size;
    }

    public String makeQueryString(int page) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("q", q)
                .queryParam("sort", sort)
                .queryParam("list", list)
                .build()
                .encode();


        return uriComponents.toUriString();
    }
}
