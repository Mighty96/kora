package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.domain.post.Board;
import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.dto.page.PageResponse;
import com.mighty.ninda.dto.user.UserPostListResponse;
import com.mighty.ninda.dto.user.UserResponse;
import com.mighty.ninda.service.OneLineCommentService;
import com.mighty.ninda.service.PostService;
import com.mighty.ninda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final OneLineCommentService oneLineCommentService;
    private final PostService postService;

    @GetMapping("/user/profile")
    public String profile(@LoginUser SessionUser sessionUser, Model model) {
        model.addAttribute("user", UserResponse.of(userService.findById(sessionUser.getId())));
        return "user/profile";
    }

    @GetMapping("/user/post")
    public String post(@LoginUser SessionUser sessionUser,
                       Model model,
                       Pageable pageable,
                       @RequestParam(required = false) Map<String, Object> searchKeyword) {


        searchKeyword.put("userId", sessionUser.getId());

        pageable.getSort().and(Sort.by("CreatedDate").descending());
        Page<Post> pagePostList = postService.findAll(searchKeyword, pageable);

        List<UserPostListResponse> userPostList = pagePostList.stream().map(UserPostListResponse::of).collect(Collectors.toList());
        PageResponse<UserPostListResponse> info = PageResponse.of(pagePostList, userPostList);

        model.addAttribute("info", info);

        return "user/post";
    }

//    @GetMapping("/user/oneLineComment")
//    public String oneLineComment(@LoginUser SessionUser sessionUser,
//                                 Model model,
//                                 Pageable pageable) {
//
//        Page<OneLineComment> pageOneLineCommentList = oneLineCommentService.findOneLineCommentByUserIdDesc(sessionUser.getId(), pageable);
//
//        List<UserOneLineCommentListResponse> oneLineCommentList = pageOneLineCommentList.stream().map(UserOneLineCommentListResponse::of).collect(Collectors.toList());
//        PageResponse<UserOneLineCommentListResponse> info = PageResponse.of(pageOneLineCommentList, oneLineCommentList);
//
//        model.addAttribute("info", info);
//        model.addAttribute("page", page);
//        model.addAttribute("size", size);
//
//        return "user/oneLineComment";
//    }

    @GetMapping("user/password")
    public String password(@LoginUser SessionUser sessionUser) {

        return "user/password";
    }
}
