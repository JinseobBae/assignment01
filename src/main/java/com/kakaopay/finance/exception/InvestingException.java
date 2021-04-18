package com.kakaopay.finance.exception;

import com.kakaopay.finance.enums.IveErrorCode;
import org.springframework.http.HttpStatus;

public class InvestingException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final IveErrorCode errCode;

    public InvestingException(HttpStatus httpStatus, IveErrorCode errorCode) {
        super(errorCode.getErrMsg());
        this.httpStatus = httpStatus;
        this.errCode = errorCode;
    }

    public HttpStatus getHttpStatus() { return httpStatus; }

    public IveErrorCode getErrCode() { return errCode; }
}
