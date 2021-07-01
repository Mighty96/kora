package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.dto.game.GameListResponse;
import com.mighty.ninda.dto.page.PageResponse;
import com.mighty.ninda.dto.post.PostListResponse;
import com.mighty.ninda.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/board")
    public String mainBoard(Model model, Pageable pageable,
                            @LoginUser SessionUser sessionUser,
                            @RequestParam int page,
                            @RequestParam int size) {

        Page<Post> pagePostList = postService.findAll(pageable);
        List<PostListResponse> gameList = pagePostList.stream().map(PostListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pagePostList, gameList);

        model.addAttribute("info", info);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "post/postList";
    }

    @GetMapping("/board/postForm")
    public String postForm() {
        return "post/postForm";
    }
}
