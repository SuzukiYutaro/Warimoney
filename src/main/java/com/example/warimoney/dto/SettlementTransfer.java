package com.example.warimoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettlementTransfer {
    private String from;
    private String to;
    private int amount;
}
