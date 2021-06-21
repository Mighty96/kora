package com.mighty.ninda.service;

import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.game.GameRepository;
import com.mighty.ninda.exception.onelinecomment.OneLineCommentAlreadyHateException;
import com.mighty.ninda.exception.onelinecomment.OneLineCommentAlreadyLikeException;
import com.mighty.ninda.utils.Crawler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final Crawler crawler;

    @Transactional
    public Game findById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임이 없습니다. id = " + id));

        return game;
    }

    @Transactional
    public Page<Game> findAll(Pageable pageable) {

        return gameRepository.findAll(pageable);
    }

    @Transactional
    public List<Game> search(String q, Pageable pageable) {
        return gameRepository.findByTitleContainingIgnoreCase(q, pageable);
    }


    @Transactional
    public void viewCountUp(Long id) {
        Game game = findById(id);
        game.viewCountUp();
    }

    @Transactional
    public Long reLikeUp(Long userId, Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임이 없습니다. id = " + gameId));

        String _userId = "[" + userId.toString() + "]";

        if (game.getLikeList().contains(_userId)) {
            throw new OneLineCommentAlreadyLikeException("이미 추천했습니다.");
        } else {
            game.reLikeUp();
            game.updateLikeList(_userId);
        }

        return game.getId();
    }

    @Transactional
    public Long reHateUp(Long userId, Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임이 없습니다. id = " + gameId));

        String _userId = "[" + userId.toString() + "]";

        if (game.getHateList().contains(_userId)) {
            throw new OneLineCommentAlreadyHateException("이미 비추천했습니다.");
        } else {
            game.reHateUp();
            game.updateHateList(_userId);
        }


        return game.getId();
    }

    public void gameCrawl() {
        crawler.crawl();
    }
}
