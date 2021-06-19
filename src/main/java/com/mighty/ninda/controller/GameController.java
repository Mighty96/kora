package com.mighty.ninda.controller;

import com.mighty.ninda.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class GameController {

    private final GameService gameService;

    @GetMapping("/game")
    public String gameList(Model model, Pageable pageable,
                           @RequestParam int page,
                           @RequestParam int size,
                           @RequestParam List<String> sort) {
        model.addAttribute("gameList", gameService.findAll(pageable));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        return "game/gameList";
    }

    @GetMapping("/game/{id}")
    public String game(Model model, @PathVariable Long id) {
        model.addAttribute("game", gameService.findById(id));
        return "game/game";
    }
}
