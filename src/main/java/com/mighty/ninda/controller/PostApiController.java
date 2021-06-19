package com.mighty.ninda.controller;

import com.mighty.ninda.service.PostService;
import com.mighty.ninda.dto.post.PostResponseDto;
import com.mighty.ninda.dto.post.PostSaveRequestDto;
import com.mighty.ninda.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/post")
    public Long save(@RequestBody PostSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    @PutMapping("/api/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    @GetMapping("/api/post/{id}")
    public PostResponseDto findById (@PathVariable Long id) {
        return postService.findById(id);
    }
}
