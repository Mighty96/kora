package com.mighty.ninda.service;

import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.domain.post.PostRepository;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.dto.post.SavePost;
import com.mighty.ninda.dto.post.UpdatePost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(SavePost requestDto, User user) {
        return postRepository.save(requestDto.toEntity(user)).getId();
    }

    @Transactional
    public Long update(Long id, UpdatePost requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        post.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public Post findById (Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return post;
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedDateDesc(pageable);
    }
}
