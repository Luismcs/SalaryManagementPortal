package com.finalproject.portal.config;

import com.finalproject.portal.exception.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CollaboratorNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleCollaboratorNotFoundException(CollaboratorNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getCollaboratorId(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({AddressNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleAddressNotFoundException(AddressNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getCollaboratorId(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({UsernameAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getUsername(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({NifAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> handleNifAlreadyExistsException(NifAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getNif(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getParam(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({RefreshTokenNotValid.class})
    public ResponseEntity<ErrorResponse> handleRefreshTokenNotValid(RefreshTokenNotValid ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getParam(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({RefreshTokenStillValidException.class})
    public ResponseEntity<ErrorResponse> handleRefreshTokenStillValidException(RefreshTokenStillValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getParam(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({UserCredentialsNotFound.class})
    public ResponseEntity<ErrorResponse> handleUserCredentialsNotFound(UserCredentialsNotFound ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getUserCredentialsId().toString(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({ComponentTypeNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleComponentTypeNotFoundException(ComponentTypeNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getComponentTypeId().toString(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({ComponentTypeSubtypeNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleComponentTypeSubtypeNotFoundException(ComponentTypeSubtypeNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getComponentTypeSubtypeId().toString(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({SalaryComponentNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleSalaryComponentNotFoundException(SalaryComponentNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getSalaryComponentId().toString(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({SalaryNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleSalaryNotFoundException(SalaryNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getSalaryId().toString(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({SalaryCannotBeUpdatedException.class})
    public ResponseEntity<ErrorResponse> handleSalaryNotUpdatableException(SalaryCannotBeUpdatedException customException) {
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
    public ResponseEntity<ErrorResponse> handleSalaryAlreadyActiveException(SalaryAlreadyActiveException customException) {
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
    public ResponseEntity<ErrorResponse> handleSalaryAlreadyApprovedException(SalaryAlreadyApprovedException customException) {
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
    public ResponseEntity<ErrorResponse> handleSalaryCannotBeApprovedException(SalaryCannotBeApprovedException customException) {
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

    @ExceptionHandler({RoleNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(
            RoleNotFoundException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getRoleId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }

}
