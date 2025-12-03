package com.example.Dream;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    public static final String LOGIN_USER = "loginUser";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String loginId,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userService.login(loginId, password);

        if (user == null) {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }

        // 로그인 성공 → 세션에 저장
        session.setAttribute(LOGIN_USER, user);

        // 로그인 후 꿈 목록 또는 메인으로 이동
        return "redirect:/dream/list";
    }

    // 회원가입 폼
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String loginId,
                           @RequestParam String password,
                           @RequestParam String name,
                           Model model) {

        try {
            userService.register(loginId, password, name);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        // 가입 후 로그인 페이지로
        return "redirect:/login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 전체 삭제
        return "redirect:/login";
    }
}
