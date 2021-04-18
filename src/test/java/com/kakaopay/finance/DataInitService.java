package com.kakaopay.finance;

import com.kakaopay.finance.investing.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitService {

    @Autowired
    protected InvestingItemRepository investingItemRepository;
    @Autowired
    protected InvestingItemStatusRepository investingItemStatusRepository;
    @Autowired
    protected InvestingRepository investingRepository;

    public void dataInit(){
        InvestingItemStatus status = InvestingItemStatus.builder()
                .investorAmount(1)
                .nowInvestingAmount(1000)
                .itemStatus("1")
                .build();
        investingItemStatusRepository.save(status);

        InvestingItem item = InvestingItem.builder()
                .title("투자중")
                .totalInvestingAmount(100000)
                .startedAt(LocalDateTime.of(2020,3,1,0,0,0))
                .finishedAt(LocalDateTime.of(2022,4,1,0,0,0))
                .investingItemStatus(status)
                .build();
        investingItemRepository.save(item);



        InvestingItemStatus closeStatus = InvestingItemStatus.builder()
                .investorAmount(10)
                .nowInvestingAmount(6000)
                .itemStatus("0")
                .build();
        investingItemStatusRepository.save(closeStatus);

        InvestingItem closedItem = InvestingItem.builder()
                .title("투자종료")
                .totalInvestingAmount(50000)
                .startedAt(LocalDateTime.of(2020,3,1,0,0,0))
                .finishedAt(LocalDateTime.of(2022,4,1,0,0,0))
                .investingItemStatus(closeStatus)
                .build();
        investingItemRepository.save(closedItem);
    }


}
