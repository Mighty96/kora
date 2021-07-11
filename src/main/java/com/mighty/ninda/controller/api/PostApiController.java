package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.service.PostService;
import com.mighty.ninda.dto.post.SavePost;
import com.mighty.ninda.dto.post.UpdatePost;
import com.mighty.ninda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping("/api/posts")
    public Long savePost(@RequestBody SavePost requestDto,
                     @LoginUser SessionUser sessionUser) {
        return postService.save(requestDto, userService.findById(sessionUser.getId()));
    }


    @PostMapping("/api/posts/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody UpdatePost requestDto) {
        log.info("asd");
        return postService.update(id, requestDto);
    }

    @DeleteMapping("/api/posts/{id}")
    public Long deletePost(@PathVariable Long id) {
        return postService.delete(id);
    }

    @GetMapping("/api/posts/{id}/like")
    public Long reLikeUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        postService.reLikeUp(sessionUser.getId(), id);

        return id;
    }

    @GetMapping("/api/posts/{id}/hate")
    public Long reHateUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        postService.reHateUp(sessionUser.getId(), id);

        return id;
    }
}
