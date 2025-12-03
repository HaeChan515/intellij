package com.example.Dream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // 웹사이트 첫 접속 시 로그인 페이지로 이동
        return "redirect:/login";
    }
}
