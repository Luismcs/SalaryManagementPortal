package com.finalproject.collaborator.exception;

import com.finalproject.collaborator.enums.ErrorResponseCode;

public class AddressNotFoundException extends CustomException {

    private Long addressId;

    public AddressNotFoundException(ErrorResponseCode errorResponseCode, int status, String message, Long addressId) {
        super(errorResponseCode, status, message);
        this.addressId = addressId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
