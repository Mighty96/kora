package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.service.PostService;
import com.mighty.ninda.dto.post.SavePost;
import com.mighty.ninda.dto.post.UpdatePost;
import com.mighty.ninda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/api/posts")
    public Long savePost(@RequestBody SavePost requestDto,
                     @LoginUser CurrentUser currentUser) {
        return postService.save(requestDto, userService.findById(currentUser.getId()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/api/posts/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody UpdatePost requestDto) {
        postService.update(id, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/api/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.delete(id);
    }


    @GetMapping("/api/posts/{id}/like")
    public void reLikeUp(@LoginUser CurrentUser currentUser,
                         @PathVariable Long id) {

        postService.reLikeUp(currentUser, id);
    }

    @GetMapping("/api/posts/{id}/hate")
    public void reHateUp(@LoginUser CurrentUser currentUser,
                         @PathVariable Long id) {
        postService.reHateUp(currentUser, id);
    }
}
