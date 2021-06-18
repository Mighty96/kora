package com.mighty.kora.controller;

import com.mighty.kora.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GameApiController {

    private final GameService gameService;

    @GetMapping("/api/gameCrawl")
    public void gameCrawl() {
        gameService.gameCrawl();
    }
}
