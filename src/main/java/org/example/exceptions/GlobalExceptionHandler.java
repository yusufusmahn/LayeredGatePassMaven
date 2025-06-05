package org.example.exceptions;

import org.example.dtos.responses.ApiResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GatePassException.class)
    public ResponseEntity<ApiResponse> handleGatePassException(GatePassException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception e) {
        return new ResponseEntity<>(new ApiResponse("An unexpected error occurred: " + e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}