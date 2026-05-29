package com.example.warimoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.warimoney.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

	private final ExpenseService expenseService;

	// 支払い記録追加
	@PostMapping("/projects/{projectId}/expenses")
	public String addExpense(
			@PathVariable Long projectId,
			@RequestParam Long payerId,
			@RequestParam Double amount,
			@RequestParam String description) {
		expenseService.addExpense(projectId, payerId, amount, description);
		return "redirect:/projects/" + projectId;
	}

	// 支払い記録編集
	@PostMapping("/projects/{projectId}/expenses/{expenseId}/edit")
	public String editExpense(
			@PathVariable Long projectId,
			@RequestParam Long payerId,
			@PathVariable Long expenseId,
			@RequestParam Double amount,
			@RequestParam String description) {
		expenseService.editExpense(expenseId, payerId, amount, description);
		return "redirect:/projects/" + projectId;
	}

	// 支払い記録削除
	@PostMapping("/projects/{projectId}/expenses/{expenseId}/delete")
	public String deleteExpense(
			@PathVariable Long projectId,
			@PathVariable Long expenseId,
			RedirectAttributes redirectAttributes) {
		try {
			expenseService.deleteExpense(expenseId);
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/projects/" + projectId;
		}
		return "redirect:/projects/" + projectId;
	}

}
