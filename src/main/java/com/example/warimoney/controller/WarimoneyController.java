package com.example.warimoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WarimoneyController {
	
	@GetMapping("/tologin")
	public String index() {
		return "warimoney/login";
	}
	
	@GetMapping("/projects")
	public String projects() {
		return "warimoney/projects";
	}

}
