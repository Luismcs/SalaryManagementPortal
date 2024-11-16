package com.finalproject.portal.exception;

import com.finalproject.portal.enums.ErrorResponseCode;

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
