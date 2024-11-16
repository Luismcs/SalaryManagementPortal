package com.finalproject.portal.exception;

import com.finalproject.portal.enums.ErrorResponseCode;

public class NifAlreadyExistsException extends CustomException {
    private String nif;

    public NifAlreadyExistsException(ErrorResponseCode errorResponseCode, int status, String message, String nif) {
        super(errorResponseCode, status, message);
        this.nif = nif;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}
