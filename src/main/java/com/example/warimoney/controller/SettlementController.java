package com.example.warimoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.warimoney.service.ProjectService;
import com.example.warimoney.service.SettlementService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SettlementController {

	private final ProjectService projectService;
	private final SettlementService settlementService;

	@GetMapping("/projects/{projectId}/settlement")
	public String settlement(
			@PathVariable Long projectId,
			Model model) {
		model.addAttribute("project", projectService.getProject(projectId));
		model.addAttribute("results", settlementService.calculateSettlement(projectId));
		model.addAttribute("transfers", settlementService.calculateTransfers(projectId));
		return "/warimoney/settlement";
	}
}
