package com.mighty.ninda.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    Page<Post> findByBoardOrderByCreatedDateDesc(Board board, Pageable pageable);

//    Page<Post> findByBoardAndByUserIdOrderByCreatedDateDesc(Board board, Long userId, Pageable pageable);

    List<Post> findTop10ByBoardOrderByCreatedDateDesc(Board board);

//    Page<Post> findAllOrderByPostIdDesc(Specification<Post> spec, Pageable pageable);
}
