package com.mighty.ninda.domain.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OneLineCommentRepository extends JpaRepository<OneLineComment, Long> {

    List<OneLineComment> findByGameId(Long gameId);
    List<OneLineComment> findTop5ByOrderByCreatedDateDesc();
//    Page<OneLineComment> findByUserIdOrderByIdDesc(Long userId, Pageable pageable);
}
