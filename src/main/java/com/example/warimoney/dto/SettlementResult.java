package com.example.warimoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettlementResult {
    private Long memberId;
    private String memberName;
    private int paidAmount;     // 支払額
    private int shareAmount;    // 負担額
    private int balance;        // 差額（+なら受け取る、-なら払う）
}
