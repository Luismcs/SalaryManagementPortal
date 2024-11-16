package com.finalproject.portal.exception;

import com.finalproject.portal.enums.ErrorResponseCode;

public class CollaboratorNotFoundException extends CustomException {
    private String collaboratorId;

    public CollaboratorNotFoundException(ErrorResponseCode errorResponseCode, int status, String message, String collaboratorId) {
        super(errorResponseCode, status, message);
        this.collaboratorId = collaboratorId;
    }

    public String getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(String collaboratorId) {
        this.collaboratorId = collaboratorId;
    }
}
