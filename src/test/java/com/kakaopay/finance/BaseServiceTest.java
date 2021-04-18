package com.kakaopay.finance;

import com.kakaopay.finance.investing.application.service.InvestingSearchService;
import com.kakaopay.finance.investing.application.service.InvestingService;
import com.kakaopay.finance.investing.domain.InvestingItemRepository;
import com.kakaopay.finance.investing.domain.InvestingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class BaseServiceTest {

    @Autowired
    protected InvestingSearchService investingSearchService;
    @Autowired
    protected InvestingService investingService;
    @Autowired
    protected DataInitService dataInitService;
    @Autowired
    protected InvestingRepository investingRepository;
    @Autowired
    protected InvestingItemRepository investingItemRepository;


}
