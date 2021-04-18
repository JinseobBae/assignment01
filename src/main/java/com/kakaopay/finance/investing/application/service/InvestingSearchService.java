package com.kakaopay.finance.investing.application.service;

import com.kakaopay.finance.investing.application.dto.InvestingDetailDto;
import com.kakaopay.finance.investing.application.dto.InvestingItemDto;

import java.util.List;

public interface InvestingSearchService {

    List<InvestingItemDto> findAllInvestingItem();


    List<InvestingDetailDto> findMyInvestingHistory(long userId);
}
