package com.kakaopay.finance.exception;

import com.kakaopay.finance.enums.IveErrorCode;
import org.springframework.http.HttpStatus;

public class MinimumAmountException extends InvestingException {

    public MinimumAmountException(HttpStatus httpStatus, IveErrorCode errorCode) {
        super(httpStatus, errorCode);
    }
}
