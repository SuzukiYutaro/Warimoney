package com.example.warimoney.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.warimoney.common.AppUserDetails;
import com.example.warimoney.domain.Project;
import com.example.warimoney.domain.User;
import com.example.warimoney.service.ExpenseService;
import com.example.warimoney.service.MemberService;
import com.example.warimoney.service.ProjectService;
import com.example.warimoney.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProjectController {
	
	private final UserService userService;
	private final ProjectService projectService;
	private final MemberService memberService;
	private final ExpenseService expenseService;
	
	// プロジェクト一覧ページ
	@GetMapping("/projects")
	public String user(@AuthenticationPrincipal AppUserDetails userDetails, Model model) {
		Long userId = userDetails.getUser().getId();
		User user = userService.getUserWithProjects(userId);
		model.addAttribute("projects", user.getProjects());
		return "warimoney/projects";
	}
	
	@PostMapping("/projects")
	public String createProject(
			@AuthenticationPrincipal AppUserDetails userDetails,
			@RequestParam String projectName) {
		Long userId = userDetails.getUser().getId();
		projectService.createProject(userId, projectName);
		return "redirect:/projects";	
	}
	
	@PostMapping("/projects/{id}/edit")
	public String editProject(
			@PathVariable Long id,
			@RequestParam String projectName) {
		projectService.editProject(id, projectName);
		return "redirect:/projects";
	}
	
	@PostMapping("/projects/{id}/delete")
	public String deleteProject(@PathVariable Long id) {
		projectService.deleteProject(id);
		return "redirect:/projects";
	}

	
	// プロジェクトの詳細ページ
	@GetMapping("/projects/{id}")
	public String detail(@PathVariable Long id, Model model) {
		Project projectDetail = projectService.getProject(id);
		model.addAttribute("project", projectDetail);
		return "warimoney/project_detail";
	}

	
	@PostMapping("/projects/{id}/members")
	public String addMember(
			@PathVariable Long id,
			@RequestParam String memberName) {
		memberService.addMember(id, memberName);
		return "redirect:/projects/" + id;
	}

	@PostMapping("/projects/{projectId}/members/{memberId}/edit")
	public String editMember(
			@PathVariable Long projectId,
			@PathVariable Long memberId,
			@RequestParam String memberName) {
		memberService.editMember(memberId, memberName);
		return "redirect:/projects/" + projectId;
	}

	@PostMapping("/projects/{projectId}/members/{memberId}/delete")
	public String deleteMember(
			@PathVariable Long projectId,
			@PathVariable Long memberId,
			RedirectAttributes redirectAttributes) {
		try {
			memberService.deleteMember(memberId);
		} catch (IllegalStateException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/projects/" + projectId;
		}
		return "redirect:/projects/" + projectId;
	}
	
	@PostMapping("/projects/{projectId}/expenses")
	public String addExpense(
			@PathVariable Long projectId,
			@RequestParam Long payerId,
			@RequestParam Double amount,
			@RequestParam String description
			) {
		expenseService.addExpense(projectId, payerId, amount, description);
		return "redirect:/projects/" + projectId;
	}

	@PostMapping("/projects/{projectId}/expenses/{expenseId}/edit")
	public String editExpense(
			@PathVariable Long projectId,
			@PathVariable Long expenseId,
			@RequestParam Double amount,
			@RequestParam String description,
			@RequestParam Long payerId) {
		expenseService.editExpense(expenseId, payerId, amount, description);
		return "redirect:/projects/" + projectId;
	}

	@PostMapping("/projects/{projectId}/expenses/{expenseId}/delete")
	public String deleteExpense(
			@PathVariable Long projectId,
			@PathVariable Long expenseId) {
		expenseService.deleteExpense(expenseId);
			return "redirect:/projects/" + projectId;
		}
}
