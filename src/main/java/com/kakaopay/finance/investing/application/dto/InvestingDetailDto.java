package com.kakaopay.finance.investing.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@lombok.Generated
@Getter
@Setter
@ToString
@NoArgsConstructor
public class InvestingDetailDto {

    long itemId;
    String itemTitle;
    long totalInvestingAmount;
    long myInvestingAmount;
    LocalDateTime investingAt;

    @Builder
    public InvestingDetailDto(long itemId, String itemTitle, long totalInvestingAmount, long myInvestingAmount, LocalDateTime investingAt) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.totalInvestingAmount = totalInvestingAmount;
        this.myInvestingAmount = myInvestingAmount;
        this.investingAt = investingAt;
    }
}
