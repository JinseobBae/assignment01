package com.kakaopay.finance.investing.presentation;

import com.kakaopay.finance.investing.application.dto.InvestingDetailDto;
import com.kakaopay.finance.investing.application.dto.InvestingDto;
import com.kakaopay.finance.investing.application.dto.InvestingItemDto;
import com.kakaopay.finance.investing.application.dto.InvestingResultDto;
import com.kakaopay.finance.investing.application.service.InvestingItemService;
import com.kakaopay.finance.investing.application.service.InvestingSearchService;
import com.kakaopay.finance.investing.application.service.InvestingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/")
public class InvestingController {

    private final InvestingService investingService;
    private final InvestingSearchService investingSearchService;
    private final InvestingItemService investingItemService;

    public InvestingController(InvestingService investingService, InvestingSearchService investingSearchService, InvestingItemService investingItemService){
        this.investingService = investingService;
        this.investingSearchService = investingSearchService;
        this.investingItemService = investingItemService;
    }

    @GetMapping("investing")
    List<InvestingItemDto> findAllInvestingItem(){
        return investingSearchService.findAllInvestingItem();
    }

    @PostMapping("investing")
    InvestingResultDto investing(@RequestHeader("X-USER-ID") Long userId
                               , @RequestBody InvestingDto investingDto ) throws Exception{
        investingDto.setUserId(userId);
        return investingService.investingToItem(investingDto);
    }

    @GetMapping("my-investing")
    List<InvestingDetailDto> findMyInvesting(@RequestHeader("X-USER-ID") Long userId){
        return investingSearchService.findMyInvestingHistory(userId);
    }

    @PostMapping("add-item")
    void addItem(@RequestBody InvestingItemDto investingItemDto){
        investingItemService.addItem(investingItemDto);
    }


}
