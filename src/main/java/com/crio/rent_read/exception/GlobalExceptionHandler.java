package com.crio.rent_read.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles 403 Forbidden errors from @PreAuthorize
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDenied(AuthorizationDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Access Denied: You don't have permission to perform this action");
        errorResponse.setHttpStatus(HttpStatus.FORBIDDEN.name());
        errorResponse.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
    
    // Handles custom 404 Not Found errors
    @ExceptionHandler(resourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(resourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND.name());
        errorResponse.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // New handler for the "Rental Limit Is Enforced" test case
    @ExceptionHandler(RentalLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleRentalLimitExceeded(RentalLimitExceededException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.name());
        errorResponse.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // New handler for a book being unavailable
    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleBookUnavailable(BookUnavailableException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.name());
        errorResponse.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // New handler for unauthorized actions
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setHttpStatus(HttpStatus.FORBIDDEN.name());
        errorResponse.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
    
    // General handler for all other RuntimeExceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
        errorResponse.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
