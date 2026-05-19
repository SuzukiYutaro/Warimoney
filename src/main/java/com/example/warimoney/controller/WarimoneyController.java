package com.example.warimoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WarimoneyController {

	@GetMapping("/Login")
	public String showLoginForm() {
		return "warimoney/login";
	}

}