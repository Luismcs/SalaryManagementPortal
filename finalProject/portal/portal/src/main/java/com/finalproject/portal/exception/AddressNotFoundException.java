package com.finalproject.portal.exception;

import com.finalproject.portal.enums.ErrorResponseCode;

public class AddressNotFoundException extends CustomException {
    private String addressId;

    public AddressNotFoundException(ErrorResponseCode errorResponseCode, int status, String message, String addressId) {
        super(errorResponseCode, status, message);
        this.addressId = addressId;
    }

    public String getCollaboratorId() {
        return addressId;
    }

    public void setCollaboratorId(String addressId) {
        this.addressId = addressId;
    }
}
