package com.kakaopay.finance.exception;

import com.kakaopay.finance.enums.IveErrorCode;
import org.springframework.http.HttpStatus;

public class AmountExceedException extends InvestingException{

    public AmountExceedException(HttpStatus httpStatus, IveErrorCode errorCode) {
        super(httpStatus, errorCode);
    }
}
