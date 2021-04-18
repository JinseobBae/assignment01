package com.kakaopay.finance.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ExceptionResponse {

    String errCode;
    String message;

}
