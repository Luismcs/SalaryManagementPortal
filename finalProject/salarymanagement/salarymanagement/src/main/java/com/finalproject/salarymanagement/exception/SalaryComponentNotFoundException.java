package com.finalproject.salarymanagement.exception;

import com.finalproject.salarymanagement.enums.ErrorResponseCode;

public class SalaryComponentNotFoundException extends CustomException {

    private Long salaryComponentId;


    public SalaryComponentNotFoundException(ErrorResponseCode errorResponseCode, int status, String message, Long salaryComponentId) {
        super(errorResponseCode, status, message);
        this.salaryComponentId = salaryComponentId;
    }

    public Long getSalaryComponentId() {
        return salaryComponentId;
    }

    public void setSalaryComponentId(Long salaryComponentId) {
        this.salaryComponentId = salaryComponentId;
    }
}
