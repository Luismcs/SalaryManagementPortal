package com.finalproject.authentication.exception;

import com.finalproject.authentication.enums.ErrorResponseCode;

public class RoleNotFoundException extends CustomException {

    private Long roleId;

    public RoleNotFoundException(ErrorResponseCode errorResponseCode, int status, String message, Long roleId) {
        super(errorResponseCode, status, message);
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
