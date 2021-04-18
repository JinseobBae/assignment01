package com.kakaopay.finance.investing.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@lombok.Generated
@Getter
@Setter
@NoArgsConstructor
public class InvestingDto {

    long userId; //사용자 ID
    long itemId; //상품 ID
    long investingAmount; //투자금액


    @Builder
    public InvestingDto(long userId, long itemId, long investingAmount){
        this.userId = userId;
        this.itemId = itemId;
        this.investingAmount = investingAmount;
    }

}
