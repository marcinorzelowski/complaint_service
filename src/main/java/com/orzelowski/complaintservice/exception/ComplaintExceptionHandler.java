package com.orzelowski.complaintservice.exception;

import com.orzelowski.complaintservice.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ComplaintExceptionHandler {

    @ExceptionHandler(ComplaintDataException.class)
    public ResponseEntity<ErrorResponse> handleComplaintException(ComplaintDataException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
