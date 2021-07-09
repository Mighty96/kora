package com.mighty.ninda.controller;

import com.mighty.ninda.domain.post.Board;
import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.dto.game.GameListResponse;
import com.mighty.ninda.dto.page.PageResponse;
import com.mighty.ninda.dto.post.PostListResponse;
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

    @GetMapping("/board/{board}")
    public String freeBoard(Model model, Pageable pageable,
                            @PathVariable String board) {

        Page<Post> pagePostList = null;

        for (Board bd : Board.values()) {
            if (bd.getValue().equals(board)) {
                pagePostList = postService.findByBoard(bd, pageable);
            }
        }

        List<PostListResponse> postList = pagePostList.stream().map(PostListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pagePostList, postList);

        model.addAttribute("info", info);
        model.addAttribute("board", board);
        return "post/board";
    }

    @GetMapping("/board/{board}/{id}")
    public String freePost(Model model, Pageable pageable,
                           @PathVariable String board,
                           @PathVariable Long id,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        Page<Post> pagePostList = null;

        for (Board bd : Board.values()) {
            if (bd.getValue().equals(board)) {
                pagePostList = postService.findByBoard(bd, pageable);
            }
        }

        List<PostListResponse> postList = pagePostList.stream().map(PostListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pagePostList, postList);

        model.addAttribute("info", info);

        viewCountUp(id, request, response);
        model.addAttribute("post", PostResponse.of(postService.findById(id)));
        model.addAttribute("commentList", PostCommentListResponse.of(commentService.findAllCommentByPostId(id)));
        model.addAttribute("board", board);

        return "post/post";
    }

    @GetMapping("/board/{board}/postForm")
    public String freePostForm(Model model,
                               @PathVariable String board) {
        model.addAttribute("board", board);
        return "post/postForm";
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
