package com.kakaopay.finance.investing.application.service;

import com.kakaopay.finance.exception.InvestingException;
import com.kakaopay.finance.investing.application.dto.InvestingDto;
import com.kakaopay.finance.investing.application.dto.InvestingResultDto;
import com.kakaopay.finance.investing.application.validator.InvestingValidator;
import com.kakaopay.finance.investing.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InvestingServiceImpl implements InvestingService{

    private final InvestingItemRepository investingItemRepository;
    private final InvestingRepository investingRepository;
    private final InvestingItemStatusRepository investingItemStatusRepository;
    public InvestingServiceImpl(InvestingItemRepository investingItemRepository, InvestingRepository investingRepository, InvestingItemStatusRepository investingItemStatusRepository){
        this.investingItemRepository = investingItemRepository;
        this.investingRepository = investingRepository;
        this.investingItemStatusRepository = investingItemStatusRepository;
    }


    @Override
    @Transactional
    public InvestingResultDto investingToItem(InvestingDto investingDto) throws Exception{

        //step1. Check request investing item is investable
        InvestingItem item = investingItemRepository.findValidItemsById(LocalDateTime.now(), investingDto.getItemId());
        try{
            new InvestingValidator().validateInvesting(investingDto, item);
        }catch(InvestingException ie){
            throw ie;
        }

        //step2. Investing
        boolean isNewInvestor = investingRepository.countByUserId(investingDto.getUserId()) == 0; // Check is new investor
        investingRepository.save(Investing.builder()
                .userId(investingDto.getUserId())
                .investingAmount(investingDto.getInvestingAmount())
                .investingAt(LocalDateTime.now())
                .investingItem(item)
                .build()
        );

        //step3. Update investing item status
        InvestingItemStatus investingItemStatus = item.getInvestingItemStatus();
        long nowAmount = investingItemStatus.getNowInvestingAmount(); // Now investing amount
        long toBeAmount = nowAmount + investingDto.getInvestingAmount(); // Expected amount after investing
        long goalAmount = item.getTotalInvestingAmount();  // Goal of investing amount
        investingItemStatus.increaseInvesting(investingDto.getInvestingAmount()); // nowAmount + toBeAmount
        if(isNewInvestor)
            investingItemStatus.addInvestor(); // In case of new investor, increase number of investor
        if(toBeAmount == goalAmount)
            investingItemStatus.closeInvesting(); // Change status to closed

        investingItemStatusRepository.save(investingItemStatus);

        return InvestingResultDto.builder().result("success").resultCode("1").build();
    }

}
