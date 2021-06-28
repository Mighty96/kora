package com.mighty.ninda.service;

import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.domain.post.PostRepository;
import com.mighty.ninda.dto.post.PostResponse;
import com.mighty.ninda.dto.post.SavePost;
import com.mighty.ninda.dto.post.UpdatePost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(SavePost requestDto) {
        return postRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, UpdatePost requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        post.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostResponse findById (Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostResponse(post);
    }
}
