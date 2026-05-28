package com.example.warimoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.warimoney.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

	private final ExpenseService expenseService;

	@PostMapping("/projects/{projectId}/expenses")
	public String addExpense(
			@PathVariable Long projectId,
			@RequestParam Long payerId,
			@RequestParam Double amount,
			@RequestParam String description) {
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
