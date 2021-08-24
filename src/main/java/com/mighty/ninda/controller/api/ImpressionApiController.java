package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.dto.impression.SaveImpression;
import com.mighty.ninda.dto.impression.UpdateImpression;
import com.mighty.ninda.service.ImpressionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImpressionApiController {

    private final ImpressionService impressionService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/api/impressions")
    public void saveImpression(@LoginUser SessionUser sessionUser,
                               @RequestBody SaveImpression requestDto) {

        impressionService.save(sessionUser, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/api/impressions/{id}")
    public void updateImpression(@LoginUser SessionUser sessionUser,
                                 @RequestBody UpdateImpression requestDto,
                                 @PathVariable Long id) {


        impressionService.update(sessionUser, id, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/api/impressions/{id}")
     public void deleteImpression(@LoginUser SessionUser sessionUser,
                                  @PathVariable Long id) {
        impressionService.deleteImpression(sessionUser, id);
    }

    @GetMapping("/api/impressions/{id}/like")
    public void reLikeUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        impressionService.reLikeUp(sessionUser, id);
    }

    @GetMapping("/api/impressions/{id}/hate")
    public void reHateUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        impressionService.reHateUp(sessionUser, id);
    }
}
