package com.sandesh.banking_app.Exceptions;

import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;

import javax.security.auth.login.AccountException;
import java.time.LocalTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorDetails> AccountException(AccountExceptions exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(LocalTime.now(), exception.getMessage(), webRequest.getDescription(false),
                "ACCOUNT_NOT_FOUND");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> GenericExceptions(AccountExceptions exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(LocalTime.now(), exception.getMessage(), webRequest.getDescription(false),
                "You have encountered some issue");
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
