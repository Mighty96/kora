package com.mighty.ninda.domain.hot;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HotRepository extends JpaRepository<Hot, Long> {

    @Query(value = "select h.gameId as g from Hot as h where h.createdDate > :threshold group by h.gameId order by count(h.gameId) desc, h.gameId desc")
    List<Long> findHotGame(LocalDateTime threshold, Pageable pageable);
}
