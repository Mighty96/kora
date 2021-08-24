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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
@Slf4j
public class DirectController {

    private final DirectService directService;
    private final ImpressionService impressionService;

    @GetMapping("/directs")
    public String directList(Model model) {
        model.addAttribute("directList", DirectListResponse.of(directService.findAllOrderByReleasedDateDesc()));
        return "direct/directList";
    }

    @GetMapping("/directs/{id}")
    public String direct(Model model,
                         @PathVariable Long id,
                         HttpServletRequest request,
                         HttpServletResponse response) {

        Direct direct = directService.findById(id);
        viewCountUp(id, request, response);

        model.addAttribute("direct", DirectResponse.of(direct));
        model.addAttribute("impressionList", DirectImpressionListResponse.of(impressionService.findAllImpressionByDirectId(direct.getId())));
        return "direct/direct";
    }

    /**
     조회수 중복 방지
     **/
    private void viewCountUp(Long id, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("directView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                directService.viewCountUp(id);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            directService.viewCountUp(id);
            Cookie newCookie = new Cookie("directView","[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }
}
