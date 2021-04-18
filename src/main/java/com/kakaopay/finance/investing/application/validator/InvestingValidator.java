package com.kakaopay.finance.investing.application.validator;

import com.kakaopay.finance.investing.application.dto.InvestingDto;
import com.kakaopay.finance.investing.domain.InvestingItem;
import com.kakaopay.finance.investing.domain.InvestingItemStatus;
import com.kakaopay.finance.enums.ItemStatus;
import com.kakaopay.finance.exception.AlreadySoldOutException;
import com.kakaopay.finance.exception.AmountExceedException;
import com.kakaopay.finance.exception.MinimumAmountException;
import com.kakaopay.finance.exception.NoInvestingItemException;
import com.kakaopay.finance.enums.IveErrorCode;
import org.springframework.http.HttpStatus;

public class InvestingValidator {

    public void validateInvesting(InvestingDto investing, InvestingItem item){
        if(item == null)
            throw new NoInvestingItemException(HttpStatus.BAD_REQUEST, IveErrorCode.IVE001);

        InvestingItemStatus investingItemStatus = item.getInvestingItemStatus();
        long nowAmount = investingItemStatus.getNowInvestingAmount(); // Now investing amount
        long toBeAmount = nowAmount + investing.getInvestingAmount(); // Expected amount after investing
        long goalAmount = item.getTotalInvestingAmount();  // Goal of investing amount

        if(investingItemStatus.getItemStatus().equals(ItemStatus.CLOSE.getCode()))
            throw new AlreadySoldOutException(HttpStatus.FORBIDDEN, IveErrorCode.IVE002);

        if(investing.getInvestingAmount() <= 0)
            throw new MinimumAmountException(HttpStatus.FORBIDDEN, IveErrorCode.IVE003);

        if(toBeAmount > goalAmount)
            throw new AmountExceedException(HttpStatus.FORBIDDEN, IveErrorCode.IVE004);
    }
}
