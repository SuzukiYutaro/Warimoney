package com.example.warimoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.warimoney.service.RegistrationService;

@Controller
public class RegistrationController {

  @Autowired
  private RegistrationService registrationService;

  @GetMapping("/register")
  public String showRegistrationForm() {
    return "warimoney/register";
  }

  @PostMapping("/register")
  public String register(@RequestParam String username, @RequestParam String password, Model model) {
    if (username == null || username.isBlank() || password == null || password.isBlank()) {
      model.addAttribute("error", "ユーザー名とパスワードは必須です");
      return "warimoney/register";
    }

    registrationService.register(username.trim(), password);
    return "redirect:/login";
  }
}
