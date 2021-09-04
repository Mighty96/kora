package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.service.GameService;
import com.mighty.ninda.utils.Crawler;
import com.mighty.ninda.utils.DBScheduled;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GameApiController {

    private final GameService gameService;
    private final Crawler crawler;
    private final DBScheduled dbScheduled;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/api/gameCrawl")
    public void gameCrawl() {
        crawler.crawl();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/api/saleCrawl")
    public void saleCrawl() {
        crawler.crawlSaleGame();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/api/offSale")
    public void offSale() {
        dbScheduled.offSale();
    }

    @GetMapping("/api/games/{id}/like")
    public void reLikeUp(@LoginUser CurrentUser currentUser,
                         @PathVariable Long id) {
        gameService.reLikeUp(currentUser, id);
    }

    @GetMapping("/api/games/{id}/hate")
    public void reHateUp(@LoginUser CurrentUser currentUser,
                         @PathVariable Long id) {
        gameService.reHateUp(currentUser, id);
    }
}
