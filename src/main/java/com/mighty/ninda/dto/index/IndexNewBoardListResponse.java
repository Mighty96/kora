package com.mighty.ninda.dto.index;

import com.mighty.ninda.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndexNewBoardListResponse {

    private Long postId;
    private String postTitle;

    @Builder
    public IndexNewBoardListResponse(Long postId, String postTitle) {
        this.postId = postId;
        this.postTitle = postTitle;
    }

    public static List<IndexNewBoardListResponse> of(List<Post> posts) {
        List<IndexNewBoardListResponse> response = new ArrayList<>();

        for (Post post : posts) {
            response.add(IndexNewBoardListResponse.builder()
                    .postId(post.getId())
                    .postTitle(post.getTitle())
                    .build());
        }

        return response;
    }
}
