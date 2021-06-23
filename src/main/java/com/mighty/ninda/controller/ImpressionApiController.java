package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.dto.impression.ImpressionSaveRequestDto;
import com.mighty.ninda.dto.impression.ImpressionUpdateRequestDto;
import com.mighty.ninda.dto.oneLineComment.OneLineCommentSaveRequestDto;
import com.mighty.ninda.dto.oneLineComment.OneLineCommentUpdateRequestDto;
import com.mighty.ninda.service.ImpressionService;
import com.mighty.ninda.service.OneLineCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImpressionApiController {

    private final ImpressionService impressionService;

    @PostMapping("/api/impression")
    public Long saveImpression(@LoginUser SessionUser sessionUser,
                                   @RequestBody ImpressionSaveRequestDto requestDto) {

        log.info("comment save");
        return impressionService.save(sessionUser.getId(), requestDto);
    }

    @PostMapping("/api/impression/{id}")
    public Long updateImpression(@LoginUser SessionUser sessionUser,
                                     @RequestBody ImpressionUpdateRequestDto requestDto,
                                     @PathVariable Long id) {


        return impressionService.update(sessionUser.getId(), id, requestDto);
    }

    @DeleteMapping("/api/impression/{id}")
    public Long deleteImpression(@LoginUser SessionUser sessionUser,
                                     @PathVariable Long id) {
        return impressionService.deleteImpression(id);
    }

    @GetMapping("/api/impression/{id}/like")
    public Long reLikeUp(@LoginUser SessionUser sessionUser,
                             @PathVariable Long id) {
        return impressionService.reLikeUp(sessionUser.getId(), id);
    }

    @GetMapping("/api/impression/{id}/hate")
    public Long reHateUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        return impressionService.reHateUp(sessionUser.getId(), id);
    }
}
