package com.mighty.ninda.controller;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.dto.impression.SaveImpression;
import com.mighty.ninda.dto.impression.UpdateImpression;
import com.mighty.ninda.service.ImpressionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImpressionApiController {

    private final ImpressionService impressionService;

    @PostMapping("/api/impressions")
    public Long saveImpression(@LoginUser SessionUser sessionUser,
                                   @RequestBody SaveImpression requestDto) {

        return impressionService.save(sessionUser.getId(), requestDto);
    }

    @PostMapping("/api/impressions/{id}")
    public Long updateImpression(@LoginUser SessionUser sessionUser,
                                     @RequestBody UpdateImpression requestDto,
                                     @PathVariable Long id) {


        return impressionService.update(sessionUser.getId(), id, requestDto);
    }

    @DeleteMapping("/api/impressions/{id}")
    public Long deleteImpression(@LoginUser SessionUser sessionUser,
                                     @PathVariable Long id) {
        return impressionService.deleteImpression(id);
    }

    @GetMapping("/api/impressions/{id}/like")
    public Long reLikeUp(@LoginUser SessionUser sessionUser,
                             @PathVariable Long id) {
        return impressionService.reLikeUp(sessionUser.getId(), id);
    }

    @GetMapping("/api/impressions/{id}/hate")
    public Long reHateUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        return impressionService.reHateUp(sessionUser.getId(), id);
    }
}
