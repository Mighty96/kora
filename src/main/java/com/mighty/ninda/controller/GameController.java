package com.mighty.ninda.controller;

import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.dto.game.GameListResponse;
import com.mighty.ninda.dto.game.GameOneLineCommentListResponse;
import com.mighty.ninda.dto.game.GameResponse;
import com.mighty.ninda.dto.page.PageResponse;
import com.mighty.ninda.service.GameService;
import com.mighty.ninda.service.OneLineCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@Slf4j
public class GameController {

    private final GameService gameService;
    private final OneLineCommentService oneLineCommentService;

    @GetMapping("/games")
    public String gameList(Model model, Pageable pageable,
                           @RequestParam int page,
                           @RequestParam int size,
                           @RequestParam List<String> sort,
                           @RequestParam String q,
                           @RequestParam String list) {

        Page<Game> pageGameList = null;
        if (q.equals("")) {
            if (list.equals("all")) {
                pageGameList = gameService.findAll(pageable);
            } else {
                pageGameList = gameService.findAllBefore(pageable);
            }
        } else {
            if (list.equals("all")) {
                pageGameList = gameService.search(q, pageable);
            } else {
                pageGameList = gameService.searchBefore(q, pageable);
            }
        }
        List<GameListResponse> gameList = pageGameList.stream().map(GameListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pageGameList, gameList);

        model.addAttribute("info", info);
        model.addAttribute("page", page);
        model.addAttribute("easyPage", page + 1);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("list", list);
        model.addAttribute("q", q);
        return "game/gameList";
    }

    @GetMapping("/games/{id}")
    public String game(Model model,
                       @PathVariable Long id,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        Game game = gameService.findById(id);

        // 조회수 중복 방지
        Cookie[] cookies = request.getCookies();
        boolean flag = true;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("game" + id.toString())) {
                    flag = false;
                }
            }
        }

        if (flag) {
            gameService.viewCountUp(id);
            Cookie newCookie = new Cookie("game" + id.toString(), "game" + id.toString());
            newCookie.setMaxAge(60 * 30);
            newCookie.setPath("/");
            response.addCookie(newCookie);
        }


        model.addAttribute("game", GameResponse.of(game));
        model.addAttribute("commentList", GameOneLineCommentListResponse.of(oneLineCommentService.findAllOneLineCommentByGameId(game.getId())));
        return "game/game";
    }
}
