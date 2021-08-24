package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.dto.comment.SaveComment;
import com.mighty.ninda.dto.comment.UpdateComment;
import com.mighty.ninda.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/api/comments")
    public void saveComment(@LoginUser SessionUser sessionUser,
                                   @RequestBody SaveComment requestDto) {

        commentService.saveComment(sessionUser, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/api/comments/{id}")
    public void updateComment(@LoginUser SessionUser sessionUser,
                                     @RequestBody UpdateComment requestDto,
                                     @PathVariable Long id) {


        commentService.updateComment(sessionUser, id, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/api/comments/{id}")
    public void deleteOneLineComment(@LoginUser SessionUser sessionUser,
                                     @PathVariable Long id) {
        commentService.deleteComment(sessionUser, id);
    }

    @GetMapping("/api/comments/{id}/like")
    public void reLikeUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        commentService.reLikeUp(sessionUser, id);
    }

    @GetMapping("/api/comments/{id}/hate")
    public void reHateUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        commentService.reHateUp(sessionUser, id);
    }
}
