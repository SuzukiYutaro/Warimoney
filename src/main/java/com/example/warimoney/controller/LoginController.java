package com.example.warimoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class LoginController {

	@GetMapping("/toLogin")
	public String toLogin() {
		return "warimoney/login";
	}

	@GetMapping("/projects")
	public String user() {
		return "warimoney/projects";
	}

}
