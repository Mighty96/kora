package com.mighty.ninda.domain.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByTitle(String title);

    @Query("select g.title from Game g")
    List<String> findAllTitle();

    Page<Game> findAllByReleasedDateLessThanEqual(LocalDate localDate, Pageable pageable);

    Page<Game> findByTitleContainingIgnoreCaseAndReleasedDateLessThanEqual(String q, LocalDate localDate, Pageable pageable);

    List<Game> findTop5ByReleasedDateLessThanEqualOrderByReleasedDateDesc(LocalDate localDate);
}
