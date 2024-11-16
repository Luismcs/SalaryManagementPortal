package com.finalproject.portal.exception;

import com.finalproject.portal.enums.ErrorResponseCode;

public class RefreshTokenStillValidException extends CustomException {

    private String param;

    public RefreshTokenStillValidException(ErrorResponseCode errorResponseCode, int status, String message, String param) {
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
