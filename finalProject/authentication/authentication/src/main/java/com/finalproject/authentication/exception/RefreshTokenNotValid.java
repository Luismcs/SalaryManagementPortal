package com.finalproject.authentication.exception;

import com.finalproject.authentication.enums.ErrorResponseCode;

public class RefreshTokenNotValid extends CustomException {

    private String param;

    public RefreshTokenNotValid(ErrorResponseCode errorResponseCode, int status, String message, String param) {
        super(errorResponseCode, status, message);
        this.param = param;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
