package com.mighty.ninda.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);
    List<Comment> findByUserIdOrderByIdDesc(Long userId);
}
