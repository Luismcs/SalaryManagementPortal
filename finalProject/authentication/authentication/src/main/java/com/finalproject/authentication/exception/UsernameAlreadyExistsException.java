package com.finalproject.authentication.exception;

import com.finalproject.authentication.enums.ErrorResponseCode;

public class UsernameAlreadyExistsException extends CustomException {
    private String username;

    public UsernameAlreadyExistsException(ErrorResponseCode errorResponseCode, int status, String message,
                                          String username) {
        super(errorResponseCode, status, message);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
