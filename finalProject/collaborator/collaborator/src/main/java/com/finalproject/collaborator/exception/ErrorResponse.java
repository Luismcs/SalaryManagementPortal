package com.finalproject.collaborator.exception;

import com.finalproject.collaborator.enums.ErrorResponseCode;

import java.time.LocalDateTime;

public class ErrorResponse {

    private ErrorResponseCode errorResponseCode;
    private int status;
    private String message;
    private String params;
    private LocalDateTime localDateTime;

    public ErrorResponse(ErrorResponseCode errorResponseCode, int status, String message, String params,
                         LocalDateTime localDateTime) {
        this.errorResponseCode = errorResponseCode;
        this.status = status;
        this.message = message;
        this.params = params;
        this.localDateTime = localDateTime;
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

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

}
