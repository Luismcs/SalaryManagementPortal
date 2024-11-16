package com.finalproject.collaborator.config;

import com.finalproject.collaborator.exception.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CollaboratorNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleCollaboratorNotFoundException(CollaboratorNotFoundException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getCollaboratorId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }

    @ExceptionHandler({AddressNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleAddressNotFoundException(AddressNotFoundException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getAddressId().toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException customException) {
        ErrorResponse errorResponse = new ErrorResponse(
                customException.getErrorResponseCode(),
                customException.getStatus(),
                customException.getMessage(),
                customException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(customException.getStatus()));
    }


}
