package com.example.warimoney.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.warimoney.domain.Expense;
import com.example.warimoney.domain.ExpenseParticipant;
import com.example.warimoney.domain.Member;
import com.example.warimoney.domain.Project;
import com.example.warimoney.dto.SettlementResult;

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

        // 初期化（全員 0）
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

        // 結果を DTO にまとめる
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
                    balance
            ));
        }

        return results;
    }
}
