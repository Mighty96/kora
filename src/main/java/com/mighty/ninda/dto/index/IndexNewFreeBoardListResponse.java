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
public class IndexNewFreeBoardListResponse {

    private Long postId;
    private String postTitle;

    @Builder
    public IndexNewFreeBoardListResponse(Long postId, String postTitle) {
        this.postId = postId;
        this.postTitle = postTitle;
    }

    public static List<IndexNewFreeBoardListResponse> of(List<Post> posts) {
        List<IndexNewFreeBoardListResponse> response = new ArrayList<>();

        for (Post post : posts) {
            response.add(IndexNewFreeBoardListResponse.builder()
                    .postId(post.getId())
                    .postTitle(post.getTitle())
                    .build());
        }

        return response;
    }
}
