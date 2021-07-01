package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.service.PostService;
import com.mighty.ninda.dto.post.PostResponse;
import com.mighty.ninda.dto.post.SavePost;
import com.mighty.ninda.dto.post.UpdatePost;
import com.mighty.ninda.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping("/api/posts")
    public Long save(@RequestBody SavePost requestDto,
                     @LoginUser SessionUser sessionUser) {
        return postService.save(requestDto, userService.findById(sessionUser.getId()));
    }



    @PutMapping("/api/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody UpdatePost requestDto) {
        return postService.update(id, requestDto);
    }
}
