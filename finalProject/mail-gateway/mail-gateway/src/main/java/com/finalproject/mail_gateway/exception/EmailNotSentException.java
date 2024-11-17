package com.finalproject.mail_gateway.exception;

import com.finalproject.mail_gateway.enums.ErrorResponseCode;

public class EmailNotSentException extends CustomException{

    private String senderEmail;

    public EmailNotSentException(ErrorResponseCode errorResponseCode, int status, String message, String senderEmail) {
        super(errorResponseCode, status, message);
        this.senderEmail = senderEmail;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }
}
