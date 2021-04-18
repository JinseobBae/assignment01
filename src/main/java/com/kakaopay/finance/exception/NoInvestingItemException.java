package com.kakaopay.finance.exception;

import com.kakaopay.finance.enums.IveErrorCode;
import org.springframework.http.HttpStatus;

public class NoInvestingItemException extends InvestingException{

    public NoInvestingItemException(HttpStatus httpStatus, IveErrorCode errorCode) {
        super(httpStatus, errorCode);
    }
}
