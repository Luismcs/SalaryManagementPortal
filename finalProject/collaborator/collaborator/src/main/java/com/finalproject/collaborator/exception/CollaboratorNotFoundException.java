package com.finalproject.collaborator.exception;

import com.finalproject.collaborator.enums.ErrorResponseCode;

public class CollaboratorNotFoundException extends CustomException {
    private Long collaboratorId;

    public CollaboratorNotFoundException(ErrorResponseCode errorResponseCode, int status, String message, Long collaboratorId) {
        super(errorResponseCode, status, message);
        this.collaboratorId = collaboratorId;
    }

    public Long getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(Long collaboratorId) {
        this.collaboratorId = collaboratorId;
    }
}
