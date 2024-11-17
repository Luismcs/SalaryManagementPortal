package com.finalproject.mail_gateway.config;

import com.finalproject.mail_gateway.exception.EmailNotSentException;
import com.finalproject.mail_gateway.exception.ErrorResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EmailNotSentException.class})
    public ResponseEntity<ErrorResponse> handleEmailNotSentException(EmailNotSentException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getErrorResponseCode(),
                exception.getStatus(),
                exception.getMessage(),
                exception.getSenderEmail(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(exception.getStatus()));
    }

}
