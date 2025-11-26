package com.example.Dream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DreamController {

    private final DreamService dreamService;

    public DreamController(DreamService dreamService) {
        this.dreamService = dreamService;
    }

    // 👉 꿈 입력 화면
    @GetMapping("/dream/new")
    public String newForm() {
        return "new";   // templates/new.html
    }

    // 👉 해몽 결과 보기 (폼 전송)
    @PostMapping("/dream/result")
    public String result(@RequestParam("content") String content,
                         Model model) {

        Dream dream = dreamService.createDream(content);
        model.addAttribute("dream", dream);

        return "result";    // templates/result.html
    }

    // 👉 내가 쓴 꿈 목록 보기
    @GetMapping("/dream/list")
    public String list(Model model) {
        model.addAttribute("dreams", dreamService.getDreamList());
        return "list";      // templates/list.html
    }
}
