package com.mighty.ninda.domain.game;

import lombok.Getter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Getter
public class GameQueryString {

    private final int page;

    private final Optional<String> q;

    private final Optional<String> order;

    private final Optional<String> list;

    private final Optional<String> onSale;

    public GameQueryString(String q, String order, String list, int page, String onSale) {
        this.q = Optional.ofNullable(q);
        this.order = Optional.ofNullable(order);
        this.list = Optional.ofNullable(list);
        this.page = page;
        this.onSale = Optional.ofNullable(onSale);
    }

    public String makeQueryString(int page) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParamIfPresent("q", q)
                .queryParamIfPresent("order", order)
                .queryParamIfPresent("list", list)
                .queryParamIfPresent("onSale", onSale)
                .build()
                .encode();

        return uriComponents.toUriString();
    }

    public String releasedList() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", 0)
                .queryParamIfPresent("q", q)
                .queryParamIfPresent("order", order)
                .queryParam("list", "released")
                .queryParamIfPresent("onSale", onSale)
                .build()
                .encode();

        return uriComponents.toUriString();
    }

    public String allList() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", 0)
                .queryParamIfPresent("q", q)
                .queryParamIfPresent("order", order)
                .queryParamIfPresent("onSale", onSale)
                .build()
                .encode();

        return uriComponents.toUriString();
    }

    public String sort(String order) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", 0)
                .queryParamIfPresent("q", q)
                .queryParam("order", order)
                .queryParamIfPresent("list", list)
                .queryParamIfPresent("onSale", onSale)
                .build()
                .encode();

        return uriComponents.toUriString();
    }

    public String defaultSort() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", 0)
                .queryParamIfPresent("q", q)
                .queryParamIfPresent("list", list)
                .queryParamIfPresent("onSale", onSale)
                .build()
                .encode();

        return uriComponents.toUriString();
    }

    public String onSale() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", 0)
                .queryParamIfPresent("q", q)
                .queryParamIfPresent("order", order)
                .queryParamIfPresent("list", list)
                .queryParam("onSale", "on")
                .build()
                .encode();

        return uriComponents.toUriString();
    }

    public String offSale() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", 0)
                .queryParamIfPresent("q", q)
                .queryParamIfPresent("order", order)
                .queryParamIfPresent("list", list)
                .build()
                .encode();

        return uriComponents.toUriString();
    }
}
