package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.dto.game.GameListResponse;
import com.mighty.ninda.dto.page.PageResponse;
import com.mighty.ninda.dto.post.FreeListResponse;
import com.mighty.ninda.service.PostService;
import com.mighty.ninda.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

import static com.mighty.ninda.utils.Constants.*;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/free")
    public String freeBoard(Model model, Pageable pageable,
                            @LoginUser SessionUser sessionUser,
                            @RequestParam int page,
                            @RequestParam int size) {

        Page<Post> pagePostList = postService.findByBoardNo(FREE_BOARD_NO, pageable);
        List<FreeListResponse> postList = pagePostList.stream().map(FreeListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pagePostList, postList);

        model.addAttribute("info", info);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "post/freeBoard";
    }

    @GetMapping("/multi")
    public String multiBoard(Model model, Pageable pageable,
                            @LoginUser SessionUser sessionUser,
                            @RequestParam int page,
                            @RequestParam int size) {

        Page<Post> pagePostList = postService.findByBoardNo(MULTI_BOARD_NO, pageable);
        List<FreeListResponse> postList = pagePostList.stream().map(FreeListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pagePostList, postList);

        model.addAttribute("info", info);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "post/multiBoard";
    }

    @GetMapping("/friend")
    public String friendBoard(Model model, Pageable pageable,
                             @LoginUser SessionUser sessionUser,
                             @RequestParam int page,
                             @RequestParam int size) {

        Page<Post> pagePostList = postService.findByBoardNo(FRIEND_BOARD_NO, pageable);
        List<FreeListResponse> gameList = pagePostList.stream().map(FreeListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pagePostList, gameList);

        model.addAttribute("info", info);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "post/friendBoard";
    }

    @GetMapping("/free/freePostForm")
    public String postForm() {
        return "post/freePostForm";
    }
}
