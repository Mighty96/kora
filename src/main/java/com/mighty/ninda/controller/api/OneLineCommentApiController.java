package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.dto.oneLineComment.SaveOneLineComment;
import com.mighty.ninda.dto.oneLineComment.UpdateOneLineComment;
import com.mighty.ninda.service.OneLineCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OneLineCommentApiController {

    private final OneLineCommentService oneLineCommentService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/api/oneLineComments")
    public void saveOneLineComment(@LoginUser SessionUser sessionUser,
                                   @RequestBody SaveOneLineComment requestDto) {

        oneLineCommentService.save(sessionUser, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/api/oneLineComments/{id}")
    public void updateOneLineComment(@LoginUser SessionUser sessionUser,
                                     @RequestBody UpdateOneLineComment requestDto,
                                     @PathVariable Long id) {


        oneLineCommentService.update(sessionUser, id, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/api/oneLineComments/{id}")
    public void deleteOneLineComment(@LoginUser SessionUser sessionUser,
                                     @PathVariable Long id) {
        oneLineCommentService.deleteOneLineComment(sessionUser, id);
    }

    @GetMapping("/api/oneLineComments/{id}/like")
    public void reLikeUp(@LoginUser SessionUser sessionUser,
                             @PathVariable Long id) {
        oneLineCommentService.reLikeUp(sessionUser, id);
    }

    @GetMapping("/api/oneLineComments/{id}/hate")
    public void reHateUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        oneLineCommentService.reHateUp(sessionUser, id);
    }
}
