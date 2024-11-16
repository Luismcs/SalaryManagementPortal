package com.finalproject.salarymanagement.exception;

import com.finalproject.salarymanagement.enums.ErrorResponseCode;

import java.time.LocalDate;

public class DuplicateSalaryForImplementationDateException extends CustomException {

    private LocalDate implementationDate;


    public DuplicateSalaryForImplementationDateException(ErrorResponseCode errorResponseCode, int status, String message,
                                                         LocalDate implementationDate) {
        super(errorResponseCode, status, message);
        this.implementationDate = implementationDate;
    }

    public LocalDate getImplementationDate() {
        return implementationDate;
    }

    public void setImplementationDate(LocalDate implementationDate) {
        this.implementationDate = implementationDate;
    }
}
