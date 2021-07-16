package com.mighty.ninda.domain.game;

import com.sun.istack.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByTitle(String title);

    List<Game> findAll();

    Page<Game> findAll(@Nullable Specification<Game> spec, Pageable pageable);

    List<Game> findTop5ByReleasedDateLessThanEqualOrderByReleasedDateDesc(LocalDate localDate);
}
