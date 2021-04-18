package com.kakaopay.finance.investing.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@lombok.Generated
@Getter
@Setter
@ToString
@NoArgsConstructor
public class InvestingItemDto {

    long itemId; // 상품 ID
    String itemTitle; // 상품제목
    long totalInvestingAmount; // 총 모집 금액
    long nowInvestingAmount; // 현재 모집금액
    long investorAmount; //투자자 수
    String itemStatus; // 투자항목 상태
    LocalDateTime startedAt; // 투자시작일시
    LocalDateTime finishedAt; //투자종료일시

    @Builder
    public InvestingItemDto(long itemId, String itemTitle, long totalInvestingAmount, long nowInvestingAmount, long investorAmount, String itemStatus, LocalDateTime startedAt, LocalDateTime finishedAt) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.totalInvestingAmount = totalInvestingAmount;
        this.nowInvestingAmount = nowInvestingAmount;
        this.investorAmount = investorAmount;
        this.itemStatus = itemStatus;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }
}
