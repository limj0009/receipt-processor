package com.limj0009.receiptprocessor.Handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
    }
}
