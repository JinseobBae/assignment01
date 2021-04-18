package com.kakaopay.finance.investing.application.service;

import com.kakaopay.finance.investing.application.dto.InvestingItemDto;
import com.kakaopay.finance.investing.domain.InvestingItem;
import com.kakaopay.finance.investing.domain.InvestingItemRepository;
import com.kakaopay.finance.investing.domain.InvestingItemStatus;
import com.kakaopay.finance.investing.domain.InvestingItemStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class InvestingItemServiceImpl implements InvestingItemService{

    private final InvestingItemRepository investingItemRepository;
    private final InvestingItemStatusRepository investingItemStatusRepository;

    public InvestingItemServiceImpl(InvestingItemRepository investingItemRepository, InvestingItemStatusRepository investingItemStatusRepository) {
        this.investingItemRepository = investingItemRepository;
        this.investingItemStatusRepository = investingItemStatusRepository;
    }

    @Override
    public void addItem(InvestingItemDto investingItemDto) {
        InvestingItemStatus status = InvestingItemStatus.builder()
                    .itemStatus(investingItemDto.getItemStatus())
                    .nowInvestingAmount(investingItemDto.getNowInvestingAmount())
                    .investorAmount(investingItemDto.getInvestorAmount())
                    .build();
        investingItemStatusRepository.save(status);

        InvestingItem item = InvestingItem.builder().title(investingItemDto.getItemTitle())
                .title(investingItemDto.getItemTitle())
                .startedAt(investingItemDto.getStartedAt())
                .finishedAt(investingItemDto.getFinishedAt())
                .totalInvestingAmount(investingItemDto.getTotalInvestingAmount())
                .investingItemStatus(status)
                .build();
        investingItemRepository.save(item);

    }
}
