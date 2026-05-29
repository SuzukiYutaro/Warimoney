package com.example.warimoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.warimoney.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	// メンバー追加
	@PostMapping("/projects/{projectId}/members")
	public String addMember(
			@PathVariable Long projectId,
			@RequestParam String memberName) {
		memberService.addMember(projectId, memberName);
		return "redirect:/projects/" + projectId;
	}

	// メンバー編集
	@PostMapping("/projects/{projectId}/members/{memberId}/edit")
	public String editMember(
			@PathVariable Long projectId,
			@PathVariable Long memberId,
			@RequestParam String memberName) {
		memberService.editMember(memberId, memberName);
		return "redirect:/projects/" + projectId;
	}

	// メンバー削除
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

}
