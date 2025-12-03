package com.example.Dream;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DreamController {

    private final DreamService dreamService;

    public DreamController(DreamService dreamService) {
        this.dreamService = dreamService;
    }

    // ✅ 공통: 로그인 유저 가져오기
    private User getLoginUser(HttpSession session) {
        return (User) session.getAttribute(UserController.LOGIN_USER);
    }

    // 입력 폼 (로그인 필수)
    @GetMapping("/dream/new")
    public String newForm(HttpSession session) {

        // ✅ 로그인 안 되어 있으면 로그인 화면으로
        if (getLoginUser(session) == null) {
            return "redirect:/login";
        }

        return "new";
    }

    // 해몽 + 저장
    @PostMapping("/dream/result")
    public String result(@RequestParam("content") String content,
                         Model model,
                         HttpSession session) {

        if (getLoginUser(session) == null) {
            return "redirect:/login";
        }

        Dream dream = dreamService.createDream(content);
        model.addAttribute("dream", dream);

        return "result";
    }

    // 목록
    @GetMapping("/dream/list")
    public String list(Model model,
                       HttpSession session) {

        if (getLoginUser(session) == null) {
            return "redirect:/login";
        }

        model.addAttribute("dreams", dreamService.getDreamList());
        return "list";
    }

    // 👉 상세 페이지
    @GetMapping("/dream/{id}")
    public String detail(@PathVariable Long id,
                         Model model,
                         HttpSession session) {

        if (getLoginUser(session) == null) {
            return "redirect:/login";
        }

        Dream dream = dreamService.findById(id);
        if (dream == null) {
            return "redirect:/dream/list"; // 없으면 목록으로
        }
        model.addAttribute("dream", dream);
        return "detail";
    }

    // 👉 수정 폼
    @GetMapping("/dream/{id}/edit")
    public String editForm(@PathVariable Long id,
                           Model model,
                           HttpSession session) {

        if (getLoginUser(session) == null) {
            return "redirect:/login";
        }

        Dream dream = dreamService.findById(id);
        if (dream == null) {
            return "redirect:/dream/list";
        }
        model.addAttribute("dream", dream);
        return "edit";
    }

    // 👉 수정 처리
    @PostMapping("/dream/{id}/edit")
    public String edit(@PathVariable Long id,
                       @RequestParam("content") String content,
                       HttpSession session) {

        if (getLoginUser(session) == null) {
            return "redirect:/login";
        }

        Dream updated = dreamService.updateDream(id, content);
        if (updated == null) {
            return "redirect:/dream/list";
        }
        return "redirect:/dream/" + id;   // 수정 후 상세 페이지로
    }

    // 👉 삭제
    @PostMapping("/dream/{id}/delete")
    public String delete(@PathVariable Long id,
                         HttpSession session) {

        if (getLoginUser(session) == null) {
            return "redirect:/login";
        }

        dreamService.deleteDream(id);
        return "redirect:/dream/list";
    }
}
