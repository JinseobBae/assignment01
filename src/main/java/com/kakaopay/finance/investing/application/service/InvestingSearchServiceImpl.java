package com.kakaopay.finance.investing.application.service;

import com.kakaopay.finance.investing.application.dto.InvestingDetailDto;
import com.kakaopay.finance.investing.application.dto.InvestingItemDto;
import com.kakaopay.finance.investing.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestingSearchServiceImpl implements  InvestingSearchService{

    private final InvestingItemRepository investingItemRepository;
    private final InvestingRepository investingRepository;
    public InvestingSearchServiceImpl(InvestingItemRepository investingItemRepository, InvestingRepository investingRepository){
        this.investingItemRepository = investingItemRepository;
        this.investingRepository = investingRepository;
    }

    @Override
    @Transactional
    public List<InvestingItemDto> findAllInvestingItem() {
        List<InvestingItem> allItems = investingItemRepository.findValidItems( LocalDateTime.now() ); // 현재 시간 기준으로 유효한 항목
        return toInvestingItemDto(allItems);
    }

    @Override
    @Transactional
    public List<InvestingDetailDto> findMyInvestingHistory(long userId) {
        List<Investing> userInvestings = investingRepository.findByUserId(userId);
        return toInvestingDetailDto(userInvestings);
    }

    List<InvestingItemDto> toInvestingItemDto(List<InvestingItem> investingItems){
        return investingItems.stream()
                .map(item ->
                        InvestingItemDto.builder()
                                .itemId(item.getItemId())
                                .itemTitle(item.getTitle())
                                .totalInvestingAmount(item.getTotalInvestingAmount())
                                .nowInvestingAmount(item.getInvestingItemStatus().getNowInvestingAmount())
                                .investorAmount(item.getInvestingItemStatus().getInvestorAmount())
                                .itemStatus(item.getInvestingItemStatus().getItemStatus())
                                .startedAt(item.getStartedAt())
                                .finishedAt(item.getFinishedAt())
                                .build()
                )
                .collect(Collectors.toList());
    }

    List<InvestingDetailDto> toInvestingDetailDto(List<Investing> investings){
        return investings.stream()
                .map( investing ->
                        InvestingDetailDto.builder()
                                .itemId(investing.getInvestingItem().getItemId())
                                .itemTitle(investing.getInvestingItem().getTitle())
                                .totalInvestingAmount(investing.getInvestingItem().getTotalInvestingAmount())
                                .myInvestingAmount(investing.getInvestingAmount())
                                .investingAt(investing.getInvestingAt())
                                .build()
                ).collect(Collectors.toList());
    }
}
