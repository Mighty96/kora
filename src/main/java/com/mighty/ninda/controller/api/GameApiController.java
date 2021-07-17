package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.service.GameService;
import com.mighty.ninda.utils.Crawler;
import com.mighty.ninda.utils.DBScheduled;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GameApiController {

    private final GameService gameService;
    private final Crawler crawler;
    private final DBScheduled dbScheduled;

    @GetMapping("/api/gameCrawl")
    public void gameCrawl() {
        crawler.crawl();
    }

    @GetMapping("/api/saleCrawl")
    public void saleCrawl() {
        crawler.crawlSaleGame();
    }

    @GetMapping("/api/offSale")
    public void offSale() {
        dbScheduled.offSale();
    }

    @GetMapping("/api/games/{id}/like")
    public Long reLikeUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        gameService.reLikeUp(sessionUser.getId(), id);

        return id;
    }

    @GetMapping("/api/games/{id}/hate")
    public Long reHateUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        gameService.reHateUp(sessionUser.getId(), id);

        return id;
    }
}
