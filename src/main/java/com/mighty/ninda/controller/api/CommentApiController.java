package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.CurrentUser;
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
    public void saveComment(@LoginUser CurrentUser currentUser,
                                   @RequestBody SaveComment requestDto) {

        commentService.saveComment(currentUser, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/api/comments/{id}")
    public void updateComment(@LoginUser CurrentUser currentUser,
                                     @RequestBody UpdateComment requestDto,
                                     @PathVariable Long id) {


        commentService.updateComment(currentUser, id, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/api/comments/{id}")
    public void deleteOneLineComment(@LoginUser CurrentUser currentUser,
                                     @PathVariable Long id) {
        commentService.deleteComment(currentUser, id);
    }

    @GetMapping("/api/comments/{id}/like")
    public void reLikeUp(@LoginUser CurrentUser currentUser,
                         @PathVariable Long id) {
        commentService.reLikeUp(currentUser, id);
    }

    @GetMapping("/api/comments/{id}/hate")
    public void reHateUp(@LoginUser CurrentUser currentUser,
                         @PathVariable Long id) {
        commentService.reHateUp(currentUser, id);
    }
}
