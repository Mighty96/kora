package com.mighty.ninda.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AdminController {

    @GetMapping("/admin/main")
    public String main() {
        return "admin/main";
    }

    @GetMapping("/admin/crawl")
    public String crawl() {
        return "admin/crawl";
    }

    @GetMapping("/admin/post")
    public String post() {
        return "admin/post";
    }

    @GetMapping("/admin/direct")
    public String direct() {
        return "admin/direct";
    }

    @GetMapping("/admin/direct/directForm")
    public String directForm() {
        return "admin/directForm";
    }
}
