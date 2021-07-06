package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.dto.comment.SaveComment;
import com.mighty.ninda.dto.comment.UpdateComment;
import com.mighty.ninda.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comments")
    public Long saveComment(@LoginUser SessionUser sessionUser,
                                   @RequestBody SaveComment requestDto) {

        return commentService.saveComment(sessionUser.getId(), requestDto);
    }

    @PostMapping("/api/comments/{id}")
    public Long updateComment(@LoginUser SessionUser sessionUser,
                                     @RequestBody UpdateComment requestDto,
                                     @PathVariable Long id) {


        return commentService.updateComment(sessionUser.getId(), id, requestDto);
    }

    @DeleteMapping("/api/comments/{id}")
    public Long deleteOneLineComment(@LoginUser SessionUser sessionUser,
                                     @PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    @GetMapping("/api/comments/{id}/like")
    public Long reLikeUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        return commentService.reLikeUp(sessionUser.getId(), id);
    }

    @GetMapping("/api/comments/{id}/hate")
    public Long reHateUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        return commentService.reHateUp(sessionUser.getId(), id);
    }
}
