package com.example.warimoney.service;

import org.springframework.stereotype.Service;

import com.example.warimoney.domain.Expense;
import com.example.warimoney.domain.Member;
import com.example.warimoney.domain.Project;
import com.example.warimoney.repository.ExpenseRepository;
import com.example.warimoney.repository.MemberRepository;
import com.example.warimoney.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
	private final ProjectRepository projectRepository;
	private final MemberRepository memberRepository;
	private final ExpenseRepository expenseRepository;
	
	private final ProjectService projectService;
	
	public void addExpense(Long projectId, Long payerId, Double amount, String description) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new IllegalArgumentException("Not found"));
		Member payer = memberRepository.getReferenceById(payerId);

		Expense expense = new Expense();
		expense.setAmount(amount);
		expense.setDescription(description);
		expense.setPayer(payer);
		expense.setProject(project);

		expenseRepository.save(expense);
		
		projectService.updateTimestamp(project);
	}
	
	public void editExpense(Long expenseId, Long payerId, Double amount, String description) {
		Expense expense = expenseRepository.findById(expenseId)
				.orElseThrow();

		Member payer = memberRepository.findById(payerId)
				.orElseThrow();

		expense.setAmount(amount);
		expense.setDescription(description);
		expense.setPayer(payer);

		expenseRepository.save(expense);

		Project project = expense.getProject();
		projectService.updateTimestamp(project);
	}
	
	public void deleteExpense(Long expenseId) {
		Expense expense = expenseRepository.findById(expenseId)
				.orElseThrow();		
		expenseRepository.delete(expense);
		Project project = expense.getProject();
		projectService.updateTimestamp(project);
	}
		

}
