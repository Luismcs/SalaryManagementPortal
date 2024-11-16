package com.finalproject.salarymanagement.config;

import com.finalproject.salarymanagement.exception.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ComponentTypeNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleComponentTypeNotFoundException(
            ComponentTypeNotFoundException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getComponentTypeId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }

    @ExceptionHandler({ComponentTypeSubtypeNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleComponentTypeSubtypeNotFoundException(
            ComponentTypeSubtypeNotFoundException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getComponentTypeSubtypeId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }

    @ExceptionHandler({SalaryComponentNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleSalaryComponentNotFoundException(
            SalaryComponentNotFoundException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getSalaryComponentId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }

    @ExceptionHandler({SalaryNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleSalaryNotFoundException(SalaryNotFoundException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getSalaryId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }

    @ExceptionHandler({SalaryCannotBeUpdatedException.class})
    public ResponseEntity<ErrorResponse> handleSalaryNotUpdatableException(
            SalaryCannotBeUpdatedException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getSalaryId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }

    @ExceptionHandler({SalaryAlreadyActiveException.class})
    public ResponseEntity<ErrorResponse> handleSalaryAlreadyActiveException(
            SalaryAlreadyActiveException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getSalaryId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }

    @ExceptionHandler({SalaryAlreadyApprovedException.class})
    public ResponseEntity<ErrorResponse> handleSalaryAlreadyApprovedException(
            SalaryAlreadyApprovedException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getSalaryId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }

    @ExceptionHandler({SalaryCannotBeApprovedException.class})
    public ResponseEntity<ErrorResponse> handleSalaryCannotBeApprovedException(
            SalaryCannotBeApprovedException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getSalaryId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }

    @ExceptionHandler({DuplicateSalaryForImplementationDateException.class})
    public ResponseEntity<ErrorResponse> handleDuplicateSalaryForImplementationDateException(
            DuplicateSalaryForImplementationDateException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getImplementationDate().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }
}
