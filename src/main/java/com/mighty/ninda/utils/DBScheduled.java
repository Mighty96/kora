package com.mighty.ninda.utils;


import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.game.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DBScheduled {

    private final GameRepository gameRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void offSale() {
        List<Game> games = gameRepository.findAll();

        for (Game game : games) {
            if (game.getEndSale().isBefore(LocalDate.now())) {
                game.offSale();
            }
        }
    }
}
