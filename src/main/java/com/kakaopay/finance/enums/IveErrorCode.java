package com.kakaopay.finance.enums;

public enum IveErrorCode {

    IVE001("err.code.ive.001"),
    IVE002("err.code.ive.002"),
    IVE003("err.code.ive.003"),
    IVE004("err.code.ive.004");


    private final String errMsg;

    IveErrorCode(String errMsg){
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
