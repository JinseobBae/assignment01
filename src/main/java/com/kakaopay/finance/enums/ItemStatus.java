package com.kakaopay.finance.enums;

public enum ItemStatus {

    OPEN("1"),
    CLOSE("0");


    private final String code;


    ItemStatus(String code){
        this.code = code;
    }

    public String getCode() { return code; }

}
