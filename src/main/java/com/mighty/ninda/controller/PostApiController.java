package com.mighty.ninda.controller;

import com.mighty.ninda.service.PostService;
import com.mighty.ninda.dto.post.PostResponse;
import com.mighty.ninda.dto.post.SavePost;
import com.mighty.ninda.dto.post.UpdatePost;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/posts")
    public Long save(@RequestBody SavePost requestDto) {
        return postService.save(requestDto);
    }

    @PutMapping("/api/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody UpdatePost requestDto) {
        return postService.update(id, requestDto);
    }

    @GetMapping("/api/posts/{id}")
    public PostResponse findById (@PathVariable Long id) {
        return postService.findById(id);
    }
}
