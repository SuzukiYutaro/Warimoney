package com.example.warimoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.warimoney.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ログインページへの遷移
    @GetMapping("/login")
    public String login() {
        return "warimoney/login";
    }

    @GetMapping("/register")
    public String register() {
        return "warimoney/register";
    }

    // ユーザ登録処理
    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        try {
            userService.registerUser(username, password);
        } catch (Exception e) {
            model.addAttribute("error", "登録に失敗しました: " + e.getMessage());
            return "warimoney/register";
        }

        return "redirect:/login?registered=true";
    }

    @GetMapping("/")
    public String home() {
        return "warimoney/login";
    }

   
}
