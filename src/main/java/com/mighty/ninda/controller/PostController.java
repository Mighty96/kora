package com.mighty.ninda.controller;

import com.mighty.ninda.domain.post.PostQueryString;
import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.dto.game.GameListResponse;
import com.mighty.ninda.dto.page.PageResponse;
import com.mighty.ninda.dto.post.PostListResponse;
import com.mighty.ninda.dto.post.PostCommentListResponse;
import com.mighty.ninda.dto.post.PostResponse;
import com.mighty.ninda.dto.post.PostUpdateResponse;
import com.mighty.ninda.service.CommentService;
import com.mighty.ninda.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/board/{board}")
    public String board(@ModelAttribute("queryString") PostQueryString postQueryString,
                        Model model, @PageableDefault(size = 30) Pageable pageable,
                        @PathVariable String board) {

        Page<Post> pagePostList = getPosts(postQueryString, pageable, board);

        List<PostListResponse> postList = pagePostList.stream().map(PostListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pagePostList, postList);

        model.addAttribute("info", info);
        model.addAttribute("board", board);
        return "post/board";
    }

    @GetMapping("/board/{board}/{id}")
    public String post(@ModelAttribute("queryString") PostQueryString postQueryString,
                       Model model, @PageableDefault(size = 30) Pageable pageable,
                       @PathVariable String board,
                       @PathVariable Long id,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        Page<Post> pagePostList = getPosts(postQueryString, pageable, board);

        List<PostListResponse> postList = pagePostList.stream().map(PostListResponse::of).collect(Collectors.toList());
        PageResponse<GameListResponse> info = PageResponse.of(pagePostList, postList);

        model.addAttribute("info", info);

        viewCountUp(id, request, response);
        model.addAttribute("post", PostResponse.of(postService.findById(id)));
        model.addAttribute("commentList", PostCommentListResponse.of(commentService.findAllCommentByPostId(id)));
        model.addAttribute("board", board);

        return "post/post";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/board/{board}/write")
    public String write(Model model,
                        @PathVariable String board) {
        model.addAttribute("board", board);
        return "post/postForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/board/{board}/{id}/modify")
    public String modify(Model model,
                         @PathVariable String board,
                         @PathVariable Long id) {
        model.addAttribute("board", board);
        model.addAttribute("post", PostUpdateResponse.of(postService.findById(id)));
        return "post/modifyForm";
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

    private Page<Post> getPosts(PostQueryString postQueryString, Pageable pageable, String board) {
        Page<Post> pagePostList;
        Map<String, Object> searchKeyword = new HashMap<>();
        if (postQueryString.getS_type().isPresent()) {
            if (postQueryString.getS_type().get().equals("title")) {
                searchKeyword.put("title", postQueryString.getS_keyword().get());
            } else if (postQueryString.getS_type().get().equals("context")) {
                searchKeyword.put("context", postQueryString.getS_keyword().get());
            } else if (postQueryString.getS_type().get().equals("title_context")) {
                searchKeyword.put("title_context", postQueryString.getS_keyword().get());
            } else if (postQueryString.getS_type().get().equals("author")) {
                searchKeyword.put("userName", postQueryString.getS_keyword().get());
            }
        }

        searchKeyword.put("board", board);
        pagePostList = postService.findAll(searchKeyword, pageable);

        return pagePostList;
    }
}
