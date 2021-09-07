package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.dto.oneLineComment.SaveOneLineComment;
import com.mighty.ninda.dto.oneLineComment.UpdateOneLineComment;
import com.mighty.ninda.service.OneLineCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OneLineCommentApiController {

    private final OneLineCommentService oneLineCommentService;

    @PostMapping("/api/oneLineComments")
    public void saveOneLineComment(@LoginUser CurrentUser currentUser,
                                   @RequestBody @Valid SaveOneLineComment requestDto) {

        oneLineCommentService.save(currentUser, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/api/oneLineComments/{id}")
    public void updateOneLineComment(@LoginUser CurrentUser currentUser,
                                     @RequestBody @Valid UpdateOneLineComment requestDto,
                                     @PathVariable Long id) {


        oneLineCommentService.update(currentUser, id, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/api/oneLineComments/{id}")
    public void deleteOneLineComment(@LoginUser CurrentUser currentUser,
                                     @PathVariable Long id) {
        oneLineCommentService.deleteOneLineComment(currentUser, id);
    }

    @GetMapping("/api/oneLineComments/{id}/like")
    public void reLikeUp(@LoginUser CurrentUser currentUser,
                             @PathVariable Long id) {
        oneLineCommentService.reLikeUp(currentUser, id);
    }

    @GetMapping("/api/oneLineComments/{id}/hate")
    public void reHateUp(@LoginUser CurrentUser currentUser,
                         @PathVariable Long id) {
        oneLineCommentService.reHateUp(currentUser, id);
    }
}
