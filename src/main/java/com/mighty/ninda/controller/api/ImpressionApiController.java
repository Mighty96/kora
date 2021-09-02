package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.CurrentUser;
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

    @PostMapping("/api/impressions")
    public void saveImpression(@LoginUser CurrentUser currentUser,
                               @RequestBody SaveImpression requestDto) {

        impressionService.save(currentUser, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/api/impressions/{id}")
    public void updateImpression(@LoginUser CurrentUser currentUser,
                                 @RequestBody UpdateImpression requestDto,
                                 @PathVariable Long id) {


        impressionService.update(currentUser, id, requestDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/api/impressions/{id}")
     public void deleteImpression(@LoginUser CurrentUser currentUser,
                                  @PathVariable Long id) {
        impressionService.deleteImpression(currentUser, id);
    }

    @GetMapping("/api/impressions/{id}/like")
    public void reLikeUp(@LoginUser CurrentUser currentUser,
                         @PathVariable Long id) {
        impressionService.reLikeUp(currentUser, id);
    }

    @GetMapping("/api/impressions/{id}/hate")
    public void reHateUp(@LoginUser CurrentUser currentUser,
                         @PathVariable Long id) {
        impressionService.reHateUp(currentUser, id);
    }
}
