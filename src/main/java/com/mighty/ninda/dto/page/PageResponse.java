package com.mighty.ninda.dto.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor
@Getter
public class PageResponse<T> {

    private int nowPage;
    private int size;
    private int totalPages;
    private List<T> contents;

    private PageResponse(int nowPage, int size, int totalPages, List<T> contents) {
        this.nowPage = nowPage;
        this.size = size;
        this.totalPages = totalPages;
        this.contents = contents;
    }

    public static <T, G> PageResponse of(Page<G> entities, List<T> contents) {
        int nowPage = entities.getPageable().getPageNumber();
        int size = entities.getSize();
        int totalPages = entities.getTotalPages();

        return new PageResponse<>(nowPage, size, totalPages, contents);
    }
}