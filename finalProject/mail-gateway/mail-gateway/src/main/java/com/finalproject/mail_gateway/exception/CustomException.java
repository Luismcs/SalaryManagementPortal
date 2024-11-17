package com.finalproject.mail_gateway.exception;

import com.finalproject.mail_gateway.enums.ErrorResponseCode;

public class CustomException extends Exception {

    private ErrorResponseCode errorResponseCode;
    private int status;
    private String message;

    public CustomException(ErrorResponseCode errorResponseCode, int status, String message) {
        this.errorResponseCode = errorResponseCode;
        this.status = status;
        this.message = message;
    }

    public ErrorResponseCode getErrorResponseCode() {
        return errorResponseCode;
    }

    public void setErrorResponseCode(ErrorResponseCode errorResponseCode) {
        this.errorResponseCode = errorResponseCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
