package com.kakaopay.finance.investing.application.service;

import com.kakaopay.finance.BaseServiceTest;
import com.kakaopay.finance.exception.AlreadySoldOutException;
import com.kakaopay.finance.exception.AmountExceedException;
import com.kakaopay.finance.exception.MinimumAmountException;
import com.kakaopay.finance.exception.NoInvestingItemException;
import com.kakaopay.finance.investing.application.dto.InvestingDto;
import com.kakaopay.finance.investing.domain.InvestingItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InvestingServiceImplTest extends BaseServiceTest {

    @Autowired
    private InvestingService investingService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<InvestingItem> openItem = new ArrayList<>();
    private List<InvestingItem> closeItem = new ArrayList<>();


    @BeforeEach
    private void init(){
        dataInitService.dataInit();
        List<InvestingItem> items = investingItemRepository.findValidItems(LocalDateTime.now());
        this.openItem = items.stream().filter(item -> item.getInvestingItemStatus().getItemStatus().equals("1") ).collect(Collectors.toList());
        this.closeItem = items.stream().filter(item -> item.getInvestingItemStatus().getItemStatus().equals("0") ).collect(Collectors.toList());
    }



    @Test
    void investingSuccess() throws Exception {
        InvestingDto dto = InvestingDto.builder()
                .userId(1234)
                .itemId(openItem.get(0).getItemId())
                .investingAmount(1000)
                .build();
        investingService.investingToItem(dto);
    }

    @Test
    void investingFailed(){
        assertThrows(NoInvestingItemException.class, () -> {
            investingService.investingToItem(InvestingDto.builder()
                    .userId(1234)
                    .itemId(0)  // Invalid item id
                    .investingAmount(10000)
                    .build());
        });

        assertThrows(AmountExceedException.class, () -> {
            investingService.investingToItem(InvestingDto.builder()
                    .userId(1234)
                    .itemId(openItem.get(0).getItemId())
                    .investingAmount(999999999) // Exceed amount. Total is 1000000
                    .build());
        });

        assertThrows(MinimumAmountException.class, () -> {
            investingService.investingToItem(InvestingDto.builder()
                    .userId(1234)
                    .itemId(openItem.get(0).getItemId())
                    .investingAmount(-1) // less than minimum(1)
                    .build());
        });

        assertThrows(AlreadySoldOutException.class, () -> {
            investingService.investingToItem(InvestingDto.builder()
                    .userId(1234)
                    .itemId(closeItem.get(0).getItemId())
                    .investingAmount(100000)
                    .build());
        });
    }




}
