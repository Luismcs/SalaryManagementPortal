package com.finalproject.salarymanagement.exception;

import com.finalproject.salarymanagement.enums.ErrorResponseCode;

public class ComponentTypeSubtypeNotFoundException extends CustomException {

    private Long componentTypeSubtypeId;


    public ComponentTypeSubtypeNotFoundException(ErrorResponseCode errorResponseCode, int status, String message, Long componentTypeSubtypeId) {
        super(errorResponseCode, status, message);
        this.componentTypeSubtypeId = componentTypeSubtypeId;
    }

    public Long getComponentTypeSubtypeId() {
        return componentTypeSubtypeId;
    }

    public void setComponentTypeSubtypeId(Long componentTypeSubtypeId) {
        this.componentTypeSubtypeId = componentTypeSubtypeId;
    }
}
