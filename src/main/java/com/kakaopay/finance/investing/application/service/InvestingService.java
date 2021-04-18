package com.kakaopay.finance.investing.application.service;

import com.kakaopay.finance.investing.application.dto.InvestingDto;
import com.kakaopay.finance.investing.application.dto.InvestingResultDto;


public interface InvestingService {

    InvestingResultDto investingToItem(InvestingDto investingDoDto) throws Exception;

}
