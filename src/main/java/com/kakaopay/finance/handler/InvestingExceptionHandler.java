package com.kakaopay.finance.handler;

import com.kakaopay.finance.exception.InvestingException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice(annotations = RestController.class)
public class InvestingExceptionHandler {

    private final MessageSource messageSource;

    public InvestingExceptionHandler(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> investingExceptionHandler(InvestingException ie){

        return new ResponseEntity<>(generateResponse(ie.getErrCode().name(), ie.getErrCode().getErrMsg()), ie.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception e){
        return new ResponseEntity<>(generateResponse("INTERNAL_SERVER_ERROR", "err.code.default"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    ExceptionResponse generateResponse (String errCode, String errMsg){

       return ExceptionResponse.builder()
               .errCode(errCode)
               .message(messageSource.getMessage(errMsg, new Object[]{}, Locale.getDefault()))
               .build();
    }
}
