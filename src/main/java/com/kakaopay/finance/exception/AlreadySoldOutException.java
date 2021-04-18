package com.kakaopay.finance.exception;

import com.kakaopay.finance.enums.IveErrorCode;
import org.springframework.http.HttpStatus;

public class AlreadySoldOutException extends InvestingException{

    public AlreadySoldOutException(HttpStatus httpStatus, IveErrorCode errorCode) {
        super(httpStatus, errorCode);
    }
}
