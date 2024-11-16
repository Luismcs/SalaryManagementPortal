package com.finalproject.authentication.exception;

import com.finalproject.authentication.enums.ErrorResponseCode;

public class UserCredentialsNotFound extends CustomException {

    private Long userCredentialsId;

    public UserCredentialsNotFound(ErrorResponseCode errorResponseCode, int status, String message,
                                   Long userCredentialsId) {
        super(errorResponseCode, status, message);
        this.userCredentialsId = userCredentialsId;
    }

    public Long getUserCredentialsId() {
        return userCredentialsId;
    }

    public void setUserCredentialsId(long userCredentialsId) {
        this.userCredentialsId = userCredentialsId;
    }
}
