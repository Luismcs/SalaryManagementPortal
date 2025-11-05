package com.finalproject.authentication.config;

import com.finalproject.authentication.exception.ErrorResponse;
import com.finalproject.authentication.exception.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler({RefreshTokenNotValid.class})
    public ResponseEntity<ErrorResponse> handleRefreshTokenNotValidException(RefreshTokenNotValid ex) {
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
    public ResponseEntity<ErrorResponse> handleUserCredentialsNotFoundException(UserCredentialsNotFound ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getUserCredentialsId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler({RoleNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleRoleNotFoundExceptionException(RoleNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorResponseCode(),
                ex.getStatus(),
                ex.getMessage(),
                ex.getRoleId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatus()));
    }


}
