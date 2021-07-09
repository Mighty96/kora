package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.hot.HotRepository;
import com.mighty.ninda.domain.post.Board;
import com.mighty.ninda.dto.index.IndexHotGameListResponse;
import com.mighty.ninda.dto.index.IndexNewGameListResponse;
import com.mighty.ninda.dto.index.IndexNewBoardListResponse;
import com.mighty.ninda.dto.index.IndexOneLineCommentListResponse;
import com.mighty.ninda.service.GameService;
import com.mighty.ninda.service.OneLineCommentService;
import com.mighty.ninda.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final OneLineCommentService oneLineCommentService;
    private final GameService gameService;
    private final PostService postService;
    private final HotRepository hotRepository;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {

        model.addAttribute("commentList", IndexOneLineCommentListResponse.of(oneLineCommentService.findTop5ByOrderByCreatedDateDesc()));
        model.addAttribute("newGameList", IndexNewGameListResponse.of(gameService.findNewGame()));
        model.addAttribute("freeBoard", IndexNewBoardListResponse.of(postService.findTop10ByBoardOrderByCreatedDateDesc(Board.FREE.getValue())));
        model.addAttribute("multiBoard", IndexNewBoardListResponse.of(postService.findTop10ByBoardOrderByCreatedDateDesc(Board.MULTI.getValue())));
        List<Long> hotList = hotRepository.findHotGame(LocalDateTime.now().minusHours(1), PageRequest.of(0, 10));
        List<Game> hotGameList = new ArrayList<>();
        for (Long id : hotList) {
            hotGameList.add(gameService.findById(id));
        }
        model.addAttribute("hotGameList", IndexHotGameListResponse.of(hotGameList));

        return "index";
    }


}
