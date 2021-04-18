package com.kakaopay.finance.investing.application.service;

import com.kakaopay.finance.BaseServiceTest;
import com.kakaopay.finance.investing.application.dto.InvestingDetailDto;
import com.kakaopay.finance.investing.application.dto.InvestingItemDto;
import com.kakaopay.finance.investing.domain.Investing;
import com.kakaopay.finance.investing.domain.InvestingItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

class InvestingSearchServiceImplTest extends BaseServiceTest {

    private final InvestingSearchService investingSearchService;

    @Autowired
    public InvestingSearchServiceImplTest(InvestingSearchService investingSearchService) {
        this.investingSearchService = investingSearchService;
    }

    @BeforeEach
    private void init(){
        dataInitService.dataInit();
        List<?> items = investingSearchService.findAllInvestingItem();
        assert items.size() == 2;

    }

    /**
     * Find valid item service test
     */
    @Test
    void findAllValidItemTest(){
        List<InvestingItemDto> investingItemDtos = investingSearchService.findAllInvestingItem();
        investingItemDtos.forEach(System.out::println);
        assert investingItemDtos.size() > 0;
    }

    /**
     * Find user investing history service test
     */
    @Test
    void findAllInvestingByUser(){
        List<InvestingItem> items = investingItemRepository.findValidItems(LocalDateTime.now());
        Investing investing = Investing.builder()
                .investingAmount(800)
                .investingAt(LocalDateTime.now())
                .userId(1234)
                .investingItem(items.get(0))
                .build();
        investingRepository.save(investing);
        List<InvestingDetailDto> investingDetailDtos = investingSearchService.findMyInvestingHistory(1234);
        investingDetailDtos.forEach(System.out::println);
        assert investingDetailDtos.size() > 0;
    }



}