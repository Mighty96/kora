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

    @PostMapping("/api/oneLineComment")
    public Long saveOneLineComment(@LoginUser SessionUser sessionUser,
                                   @RequestBody OneLineCommentSaveRequestDto requestDto) {

        log.info("comment save");
        return oneLineCommentService.save(sessionUser.getId(), requestDto);
    }

    @PostMapping("/api/oneLineComment/{id}")
    public Long updateOneLineComment(@LoginUser SessionUser sessionUser,
                                     @RequestBody OneLineCommentUpdateRequestDto requestDto,
                                     @PathVariable Long id) {


        return oneLineCommentService.update(sessionUser.getId(), id, requestDto);
    }

    @DeleteMapping("/api/oneLineComment/{id}")
    public Long deleteOneLineComment(@LoginUser SessionUser sessionUser,
                                     @PathVariable Long id) {
        return oneLineCommentService.deleteOneLineComment(id);
    }

    @GetMapping("/api/oneLineComment/{id}/like")
    public Long reLikeUp(@LoginUser SessionUser sessionUser,
                             @PathVariable Long id) {
        return oneLineCommentService.reLikeUp(sessionUser.getId(), id);
    }

    @GetMapping("/api/oneLineComment/{id}/hate")
    public Long reHateUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        return oneLineCommentService.reHateUp(sessionUser.getId(), id);
    }
}
