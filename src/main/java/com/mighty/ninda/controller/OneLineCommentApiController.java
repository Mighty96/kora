package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.dto.oneLineComment.OneLineCommentSaveRequestDto;
import com.mighty.ninda.dto.oneLineComment.OneLineCommentUpdateRequestDto;
import com.mighty.ninda.service.OneLineCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OneLineCommentApiController {

    private final OneLineCommentService oneLineCommentService;

    @PostMapping("/game/{gameId}/comment")
    public Long saveOneLineComment(@LoginUser SessionUser sessionUser,
                                   @RequestBody OneLineCommentSaveRequestDto requestDto,
                                   @PathVariable Long gameId) {

        log.info("comment save");
        return oneLineCommentService.save(sessionUser.getId(), gameId, requestDto);
    }

    @PostMapping("/game/{gameId}/comment/{commentId}")
    public Long updateOneLineComment(@LoginUser SessionUser sessionUser,
                                     @RequestBody OneLineCommentUpdateRequestDto requestDto,
                                     @PathVariable Long gameId,
                                     @PathVariable Long commentId) {


        return oneLineCommentService.update(sessionUser.getId(), commentId, requestDto);
    }
}
