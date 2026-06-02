package com.example.warimoney.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.warimoney.domain.Expense;
import com.example.warimoney.domain.ExpenseParticipant;
import com.example.warimoney.domain.Member;
import com.example.warimoney.domain.Project;
import com.example.warimoney.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {

	private final ExpenseRepository expenseRepository;

	private final ProjectService projectService;
	private final MemberService memberService;

	// 支払い記録追加
	@Transactional
	public void addExpense(
			Long projectId,
			Long payerId,
			BigDecimal amount,
			String description,
			List<Long> participantIds) {
		Project project = projectService.getProject(projectId);

		Member payer = memberService.getMember(payerId);

		Expense expense = new Expense();
		expense.setAmount(amount);
		expense.setDescription(description);
		expense.setPayer(payer);
		expense.setProject(project);

		expenseRepository.save(expense);

		Integer shereAmount = calculateShareAmount(amount, participantIds);
		addParticipants(participantIds, expense, shereAmount);

		projectService.updateTimestamp(expense.getProject());

	}

	// 支払い記録編集
	@Transactional
	public void editExpense(
			Long expenseId,
			Long payerId,
			BigDecimal amount,
			String description,
			List<Long> participantIds) {
		Expense expense = getExpense(expenseId);
		Member payer = memberService.getMember(payerId);

		expense.setAmount(amount);
		expense.setDescription(description);
		expense.setPayer(payer);

		expense.getParticipants().clear();

		int shareAmount = calculateShareAmount(amount, participantIds);

		addParticipants(participantIds, expense, shareAmount);

		projectService.updateTimestamp(expense.getProject());
	}

	// 支払い記録削除
	@Transactional
	public void deleteExpense(Long expenseId) {
		Expense expense = getExpense(expenseId);

		expenseRepository.delete(expense);

		projectService.updateTimestamp(expense.getProject());
	}

	// 支払い参加者記録追加
	private void addParticipants(
			List<Long> participantIds,
			Expense expense,
			int shareAmount) {
		if (participantIds == null || participantIds.isEmpty()) {
			return;
		}

		for (Long memberId : participantIds) {
			Member member = memberService.getMember(memberId);

			ExpenseParticipant ep = new ExpenseParticipant();
			ep.setExpense(expense);
			ep.setParticipant(member);
			ep.setShareAmount(shareAmount);

			expense.getParticipants().add(ep);
		}
	}

	// 支払い記録をIDで取得
	public Expense getExpense(Long expenseId) {
		return expenseRepository.findById(expenseId)
				.orElseThrow(() -> new IllegalArgumentException("支払い記録が存在しません"));
	}

	// 割り勘計算
	private int calculateShareAmount(BigDecimal amount, List<Long> participantIds) {
		if (participantIds == null || participantIds.isEmpty()) {
			return 0;
		}

		// 金額を人数で割る（小数点以下切り捨て）
		return amount.divide(
				BigDecimal.valueOf(participantIds.size()),
				0,
				RoundingMode.DOWN).intValue();
	}

}
