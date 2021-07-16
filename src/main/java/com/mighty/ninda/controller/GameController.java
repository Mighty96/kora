package com.mighty.ninda.controller;

import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.game.GameQueryString;
import com.mighty.ninda.domain.post.Post;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@Slf4j
public class GameController {

    private final GameService gameService;
    private final OneLineCommentService oneLineCommentService;

    @GetMapping("/games")
    public String gameList(Model model, @PageableDefault(size = 12) Pageable pageable,
                           @ModelAttribute("queryString")GameQueryString gameQueryString) {

        Page<Game> pageGameList = getGames(pageable, gameQueryString);

        List<GameListResponse> gameList = pageGameList.stream().map(GameListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pageGameList, gameList);

        model.addAttribute("info", info);
        return "game/gameList";
    }

    private Page<Game> getGames(Pageable pageable, GameQueryString gameQueryString) {
        Page<Game> pageGameList;

        Map<String, Object> searchKeyword = new HashMap<>();

        if (gameQueryString.getQ().isPresent()) {
            searchKeyword.put("q", gameQueryString.getQ().get());
        }
        if (gameQueryString.getList().isPresent()) {
            searchKeyword.put("list", gameQueryString.getList().get());
        }
        if (gameQueryString.getOnSale().isPresent()) {
            searchKeyword.put("onSale", gameQueryString.getOnSale().get());
        }
        if (gameQueryString.getOrder().isPresent()) {
            searchKeyword.put("order", gameQueryString.getOrder().get());
        }

        pageGameList = gameService.findAll(searchKeyword, pageable);
        return pageGameList;
    }

    @GetMapping("/games/{id}")
    public String game(Model model,
                       @PathVariable Long id,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        viewCountUp(id, request, response);


        model.addAttribute("game", GameResponse.of(gameService.findById(id)));
        model.addAttribute("commentList", GameOneLineCommentListResponse.of(oneLineCommentService.findAllOneLineCommentByGameId(id)));
        return "game/game";
    }

    /**
     조회수 중복 방지
     **/
    private void viewCountUp(Long id, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("gameView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                gameService.viewCountUp(id);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            gameService.viewCountUp(id);
            Cookie newCookie = new Cookie("gameView","[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }
}
