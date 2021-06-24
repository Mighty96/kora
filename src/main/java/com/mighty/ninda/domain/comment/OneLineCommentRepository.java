package com.mighty.ninda.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OneLineCommentRepository extends JpaRepository<OneLineComment, Long> {

    List<OneLineComment> findByGame_Id(@Param(value = "gameId") Long gameId);
    List<OneLineComment> findTop5ByOrderByCreatedDateDesc();
}
