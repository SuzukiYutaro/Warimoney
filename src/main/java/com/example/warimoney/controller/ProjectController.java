package com.example.warimoney.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.warimoney.common.AppUserDetails;
import com.example.warimoney.domain.Project;
import com.example.warimoney.domain.User;
import com.example.warimoney.service.ProjectService;
import com.example.warimoney.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProjectController {

	private final UserService userService;
	private final ProjectService projectService;

	// プロジェクト一覧ページ
	@GetMapping("/projects")
	public String user(@AuthenticationPrincipal AppUserDetails userDetails, Model model) {
		Long userId = userDetails.getUser().getId();
		User user = userService.getUserWithProjects(userId);
		model.addAttribute("projects", user.getProjects());
		return "warimoney/projects";
	}

	//プロジェクト作成
	@PostMapping("/projects")
	public String createProject(
			@AuthenticationPrincipal AppUserDetails userDetails,
			@RequestParam String projectName) {
		Long userId = userDetails.getUser().getId();
		projectService.createProject(userId, projectName);
		return "redirect:/projects";
	}

	//プロジェクト編集
	@PostMapping("/projects/{projectId}/edit")
	public String editProject(
			@PathVariable Long projectId,
			@RequestParam String projectName) {
		projectService.editProject(projectId, projectName);
		return "redirect:/projects";
	}

	//プロジェクト削除
	@PostMapping("/projects/{projectId}/delete")
	public String deleteProject(@PathVariable Long projectId) {
		projectService.deleteProject(projectId);
		return "redirect:/projects";
	}

	// プロジェクト詳細ページ
	@GetMapping("/projects/{projectId}")
	public String detail(@PathVariable Long projectId, Model model) {
		Project projectDetail = projectService.getProject(projectId);
		model.addAttribute("project", projectDetail);
		return "warimoney/project_detail";
	}

}
