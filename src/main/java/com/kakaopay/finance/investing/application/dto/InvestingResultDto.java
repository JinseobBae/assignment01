package com.kakaopay.finance.investing.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@lombok.Generated
@Getter
@Setter
@NoArgsConstructor
public class InvestingResultDto {

    String result;
    String resultCode;

    @Builder
    public InvestingResultDto(String result, String resultCode) {
        this.result = result;
        this.resultCode = resultCode;
    }
}
