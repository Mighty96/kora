package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GameApiController {

    private final GameService gameService;

    @GetMapping("/api/gameCrawl")
    public void gameCrawl() {
        gameService.gameCrawl();
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
