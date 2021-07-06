package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.post.Board;
import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.dto.game.GameListResponse;
import com.mighty.ninda.dto.page.PageResponse;
import com.mighty.ninda.dto.post.FreeListResponse;
import com.mighty.ninda.dto.post.PostCommentListResponse;
import com.mighty.ninda.dto.post.PostResponse;
import com.mighty.ninda.service.CommentService;
import com.mighty.ninda.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/free")
    public String freeBoard(Model model, Pageable pageable,
                            @LoginUser SessionUser sessionUser,
                            @RequestParam int page,
                            @RequestParam int size) {

        Page<Post> pagePostList = postService.findByBoard(Board.FREE, pageable);
        List<FreeListResponse> postList = pagePostList.stream().map(FreeListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pagePostList, postList);

        model.addAttribute("info", info);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "post/freeBoard";
    }

    @GetMapping("/free/{id}")
    public String freePost(Model model,
                           @PathVariable Long id,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        viewCountUp(id, request, response);
        model.addAttribute("post", PostResponse.of(postService.findById(id)));
        model.addAttribute("commentList", PostCommentListResponse.of(commentService.findAllCommentByPostId(id)));


        return "post/freePost";
    }



    @GetMapping("/multi")
    public String multiBoard(Model model, Pageable pageable,
                            @LoginUser SessionUser sessionUser,
                            @RequestParam int page,
                            @RequestParam int size) {

        Page<Post> pagePostList = postService.findByBoard(Board.MULTI, pageable);
        List<FreeListResponse> postList = pagePostList.stream().map(FreeListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pagePostList, postList);

        model.addAttribute("info", info);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "post/multiBoard";
    }

    @GetMapping("/multi/{id}")
    public String multiPost(Model model,
                           @PathVariable Long id,
                           HttpServletRequest request,
                            HttpServletResponse response) {

        viewCountUp(id, request, response);
        model.addAttribute("post", PostResponse.of(postService.findById(id)));
        model.addAttribute("commentList", PostCommentListResponse.of(commentService.findAllCommentByPostId(id)));

        return "post/multiPost";
    }

    @GetMapping("/friend")
    public String friendBoard(Model model, Pageable pageable,
                             @LoginUser SessionUser sessionUser,
                             @RequestParam int page,
                             @RequestParam int size) {

        Page<Post> pagePostList = postService.findByBoard(Board.FRIEND, pageable);
        List<FreeListResponse> gameList = pagePostList.stream().map(FreeListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pagePostList, gameList);

        model.addAttribute("info", info);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "post/friendBoard";
    }

    @GetMapping("/free/PostForm")
    public String freePostForm() {
        return "post/freePostForm";
    }

    @GetMapping("/multi/PostForm")
    public String multiPostForm() {
        return "post/multiPostForm";
    }


    /**
    조회수 중복 방지
    **/
    private void viewCountUp(Long id, HttpServletRequest request, HttpServletResponse response) {

        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                postService.viewCountUp(id);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            postService.viewCountUp(id);
            Cookie newCookie = new Cookie("postView","[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }
}
