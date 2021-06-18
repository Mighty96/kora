package com.mighty.kora.service;

import com.mighty.kora.domain.game.Game;
import com.mighty.kora.domain.game.GameRepository;
import com.mighty.kora.utils.Crawler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final Crawler crawler;

    public Game findById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임이 없습니다. id = " + id));

        return game;
    }

    public List<Game> findAll(int page) {
        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by(Sort.Direction.DESC, "title"));
        Page<Game> gameList = gameRepository.findAll(pageRequest);

        return gameList.getContent();
    }

    public void gameCrawl() {
        crawler.crawl();
    }
}
