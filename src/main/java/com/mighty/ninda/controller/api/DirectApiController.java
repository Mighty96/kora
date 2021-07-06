package com.mighty.ninda.controller.api;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.dto.direct.SaveDirect;
import com.mighty.ninda.service.DirectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DirectApiController {

    private final DirectService directService;

    @PostMapping("/api/directs")
    public Long saveDirect(@RequestBody SaveDirect requestDto) {

        return directService.save(requestDto);
    }

    @GetMapping("/api/directs/{id}/like")
    public Long reLikeUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        directService.reLikeUp(sessionUser.getId(), id);

        return id;
    }

    @GetMapping("/api/directs/{id}/hate")
    public Long reHateUp(@LoginUser SessionUser sessionUser,
                         @PathVariable Long id) {
        directService.reHateUp(sessionUser.getId(), id);

        return id;
    }
}
