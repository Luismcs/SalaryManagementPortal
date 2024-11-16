package com.finalproject.salarymanagement.exception;

import com.finalproject.salarymanagement.enums.ErrorResponseCode;

public class SalaryCannotBeApprovedException extends CustomException {

    private Long salaryId;


    public SalaryCannotBeApprovedException(ErrorResponseCode errorResponseCode, int status, String message, Long salaryId) {
        super(errorResponseCode, status, message);
        this.salaryId = salaryId;
    }

    public Long getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Long salaryId) {
        this.salaryId = salaryId;
    }
}
