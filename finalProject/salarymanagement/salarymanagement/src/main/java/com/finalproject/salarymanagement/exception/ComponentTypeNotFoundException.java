package com.finalproject.salarymanagement.exception;

import com.finalproject.salarymanagement.enums.ErrorResponseCode;

public class ComponentTypeNotFoundException extends CustomException {

    private Long componentTypeId;


    public ComponentTypeNotFoundException(ErrorResponseCode errorResponseCode, int status, String message, Long componentTypeId) {
        super(errorResponseCode, status, message);
        this.componentTypeId = componentTypeId;
    }

    public Long getComponentTypeId() {
        return componentTypeId;
    }

    public void setComponentTypeId(Long componentTypeId) {
        this.componentTypeId = componentTypeId;
    }
}
