package com.example.warimoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettlementResult {
    private Long memberId;
    private String memberName;
    private int paidAmount;
    private int shareAmount;
    private int balance;
}
