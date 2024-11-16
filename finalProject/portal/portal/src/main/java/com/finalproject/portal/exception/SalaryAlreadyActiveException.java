package com.finalproject.portal.exception;

import com.finalproject.portal.enums.ErrorResponseCode;

public class SalaryAlreadyActiveException extends CustomException {

    private Long salaryId;


    public SalaryAlreadyActiveException(ErrorResponseCode errorResponseCode, int status, String message, Long salaryId) {
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
