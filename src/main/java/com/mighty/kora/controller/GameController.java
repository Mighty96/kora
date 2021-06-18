package com.mighty.kora.controller;

import com.mighty.kora.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class GameController {

    private final GameService gameService;

    @GetMapping("/game")
    public String gameList(Model model, @RequestParam int page) {
        model.addAttribute("gameList", gameService.findAll(page));
        model.addAttribute("page", page);
        return "game/gameList";
    }

    @GetMapping("/game/{id}")
    public String game(Model model, @PathVariable Long id) {
        model.addAttribute("game", gameService.findById(id));
        return "game/game";
    }
}
