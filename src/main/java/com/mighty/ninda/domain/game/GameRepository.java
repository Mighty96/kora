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

    Page<Game> findAll(Pageable pageable);

    List<Game> findByTitleContainingIgnoreCase(String q, Pageable pageable);

    List<Game> findTop5ByReleasedDateLessThanOrderByReleasedDateDesc(LocalDate localDate);
}
