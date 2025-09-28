package com.fsole.bh.infrastructure.adapter.in.rest;

import com.fsole.bh.domain.exception.*;
import com.fsole.bh.infrastructure.adapter.in.rest.dto.ErrorResponse;
import com.fsole.bh.infrastructure.adapter.in.rest.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExternalServiceTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleTimeout(ExternalServiceTimeoutException ex) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(ResponseBuilder.error(ex.getMessage()));
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleTimeout(InvalidRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseBuilder.error(ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseBuilder.error(ex.getMessage()));
    }

}
