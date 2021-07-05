package com.mighty.ninda.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByBoardNoOrderByCreatedDateDesc(Long boardNo, Pageable pageable);

    List<Post> findTop10ByBoardNoOrderByCreatedDateDesc(Long boardNo);

}
