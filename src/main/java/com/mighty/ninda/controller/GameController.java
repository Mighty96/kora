package com.mighty.ninda.controller;

import com.mighty.ninda.domain.comment.OneLineComment;
import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.service.GameService;
import com.mighty.ninda.service.OneLineCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class GameController {

    private final GameService gameService;
    private final OneLineCommentService oneLineCommentService;

    @GetMapping("/game")
    public String gameList(Model model, Pageable pageable,
                           @RequestParam int page,
                           @RequestParam int size,
                           @RequestParam List<String> sort,
                           @RequestParam String q) {
        if (q.equals("")) {
            model.addAttribute("gameList", gameService.findAll(pageable));
        } else {
            model.addAttribute("gameList", gameService.search(q, pageable));
        }
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("q", q);
        return "game/gameList";
    }

    @GetMapping("/game/{id}")
    public String game(Model model, @PathVariable Long id) {
        Game game = gameService.findById(id);
        gameService.viewCountUp(id);
        model.addAttribute("game", game);
        model.addAttribute("description", game.getDescription().replace("\n", "<br>"));
        model.addAttribute("comments", oneLineCommentService.findAllOneLineCommentByGameId(game.getId()));
        return "game/game";
    }
}
