package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.dto.direct.SaveDirect;
import com.mighty.ninda.service.DirectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DirectApiController {

    private final DirectService directService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/directs")
    public void saveDirect(@RequestBody SaveDirect requestDto) {

        directService.save(requestDto);
    }

    @GetMapping("/api/directs/{id}/like")
    public void reLikeUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        directService.reLikeUp(sessionUser, id);
    }

    @GetMapping("/api/directs/{id}/hate")
    public void reHateUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        directService.reHateUp(sessionUser, id);
    }
}
