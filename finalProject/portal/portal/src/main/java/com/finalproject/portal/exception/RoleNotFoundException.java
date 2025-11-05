package com.finalproject.portal.exception;

import com.finalproject.portal.enums.ErrorResponseCode;

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
