package com.finalproject.portal.exception;

import com.finalproject.portal.enums.ErrorResponseCode;

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
