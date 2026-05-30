package com.example.warimoney.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.warimoney.domain.Expense;
import com.example.warimoney.domain.Member;
import com.example.warimoney.domain.Project;
import com.example.warimoney.repository.ExpenseParticipantRepository;
import com.example.warimoney.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {

	private final ExpenseRepository expenseRepository;
	private final ExpenseParticipantRepository expenseParticipantRepository;

	private final ProjectService projectService;
	private final MemberService memberService;

	// 支払い記録追加
	@Transactional
	public void addExpense(
			Long projectId,
			Long payerId,
			Double amount,
			String description) {
		Project project = projectService.getProject(projectId);

		Member payer = memberService.getMember(payerId);

		Expense expense = new Expense();
		expense.setAmount(amount);
		expense.setDescription(description);
		expense.setPayer(payer);
		expense.setProject(project);

		expenseRepository.save(expense);
		Project updatedProject = expense.getProject();
		projectService.updateTimestamp(updatedProject);

	}

	// 支払い記録編集
	@Transactional
	public void editExpense(
			Long expenseId,
			Long payerId,
			Double amount,
			String description) {
		Expense expense = getExpense(expenseId);
		Member payer = memberService.getMember(payerId);

		expense.setAmount(amount);
		expense.setDescription(description);
		expense.setPayer(payer);

		expenseRepository.save(expense);

		Project project = expense.getProject();
		projectService.updateTimestamp(project);
	}

	// 支払い記録削除
	@Transactional
	public void deleteExpense(Long expenseId) {
		Expense expense = getExpense(expenseId);

		expenseRepository.delete(expense);

		Project project = expense.getProject();
		projectService.updateTimestamp(project);
	}

	// 支払い記録をIDで取得
	public Expense getExpense(Long expenseId) {
		return expenseRepository.findById(expenseId)
				.orElseThrow(() -> new IllegalArgumentException("支払い記録が存在しません"));
	}

}
