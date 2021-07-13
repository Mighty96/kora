package com.mighty.ninda.controller;

import com.mighty.ninda.domain.direct.Direct;
import com.mighty.ninda.dto.direct.DirectListResponse;
import com.mighty.ninda.dto.direct.DirectResponse;
import com.mighty.ninda.dto.direct.DirectImpressionListResponse;
import com.mighty.ninda.service.DirectService;
import com.mighty.ninda.service.ImpressionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
@Slf4j
public class DirectController {

    private final DirectService directService;
    private final ImpressionService impressionService;

    @GetMapping("/directs")
    public String directList(Model model) {
        model.addAttribute("directList", DirectListResponse.of(directService.findAll()));
        return "direct/directList";
    }

    @GetMapping("/directs/{id}")
    public String direct(Model model, @PathVariable Long id) {
        Direct direct = directService.findById(id);
        model.addAttribute("direct", DirectResponse.of(direct));
        model.addAttribute("impressionList", DirectImpressionListResponse.of(impressionService.findAllImpressionByDirectId(direct.getId())));
        return "direct/direct";
    }

}
