package com.example.warimoney.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.warimoney.domain.Expense;
import com.example.warimoney.domain.ExpenseParticipant;
import com.example.warimoney.domain.Member;
import com.example.warimoney.domain.Project;
import com.example.warimoney.dto.SettlementResult;
import com.example.warimoney.dto.SettlementTransfer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettlementService {

	private final ProjectService projectService;

	// 精算結果の計算
	public List<SettlementResult> calculateSettlement(Long projectId) {

		Project project = projectService.getProject(projectId);

		// メンバーごとの集計用
		Map<Long, Integer> paidMap = new HashMap<>();
		Map<Long, Integer> shareMap = new HashMap<>();

		for (Member m : project.getMembers()) {
			paidMap.put(m.getId(), 0);
			shareMap.put(m.getId(), 0);
		}

		// 支払額の集計
		for (Expense e : project.getExpenses()) {
			Long payerId = e.getPayer().getId();
			int amount = e.getAmount().intValue();

			paidMap.put(payerId, paidMap.get(payerId) + amount);

			// 負担額の集計
			for (ExpenseParticipant ep : e.getParticipants()) {
				Long participantId = ep.getParticipant().getId();
				int share = ep.getShareAmount();

				shareMap.put(participantId, shareMap.get(participantId) + share);
			}
		}

		List<SettlementResult> results = new ArrayList<>();

		for (Member m : project.getMembers()) {
			int paid = paidMap.get(m.getId());
			int share = shareMap.get(m.getId());
			int balance = paid - share;

			results.add(new SettlementResult(
					m.getId(),
					m.getMemberName(),
					paid,
					share,
					balance));
		}

		return results;
	}

	public List<SettlementTransfer> calculateTransfers(Long projectId) {

		List<SettlementResult> results = calculateSettlement(projectId);

		// 支払う側（balance < 0）
		List<SettlementResult> payers = results.stream()
				.filter(r -> r.getBalance() < 0)
				.sorted((a, b) -> Integer.compare(Math.abs(a.getBalance()), Math.abs(b.getBalance())))
				.collect(Collectors.toList());

		// 受け取る側（balance > 0）
		List<SettlementResult> receivers = results.stream()
				.filter(r -> r.getBalance() > 0)
				.sorted((a, b) -> Integer.compare(a.getBalance(), b.getBalance()))
				.collect(Collectors.toList());

		List<SettlementTransfer> transfers = new ArrayList<>();

		int i = 0; // payers
		int j = 0; // receivers

		while (i < payers.size() && j < receivers.size()) {

			SettlementResult payer = payers.get(i);
			SettlementResult receiver = receivers.get(j);

			int pay = -payer.getBalance(); // 支払うべき金額（正の値）
			int receive = receiver.getBalance(); // 受け取るべき金額

			int amount = Math.min(pay, receive);

			transfers.add(new SettlementTransfer(
					payer.getMemberName(),
					receiver.getMemberName(),
					amount));

			// 残高更新
			payer.setBalance(payer.getBalance() + amount);
			receiver.setBalance(receiver.getBalance() - amount);

			// 支払者が支払い終わったら次へ
			if (payer.getBalance() == 0)
				i++;

			// 受取者が受け取り終わったら次へ
			if (receiver.getBalance() == 0)
				j++;
		}

		return transfers;
	}
	
	//支払額の集計
	public Map<String, Integer> calculatePaid(Long projectId) {
		Project project = projectService.getProject(projectId);
		Map<String, Integer> paidMap = new LinkedHashMap<>();

		for (Member m : project.getMembers()) {
	        paidMap.put(m.getMemberName(), 0);
	    }

	    for (Expense e : project.getExpenses()) {
	        String payer = e.getPayer().getMemberName();
	        int amount = e.getAmount().intValue();
	        paidMap.put(payer, paidMap.get(payer) + amount);
	    }
		return paidMap;
	}

}
