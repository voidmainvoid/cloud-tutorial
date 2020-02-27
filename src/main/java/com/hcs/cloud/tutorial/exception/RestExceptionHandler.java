package com.hcs.cloud.tutorial.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<Object> handleAllExceptions(Exception ex,
    // WebRequest request) {
    // ExceptionResponse resp = new ExceptionResponse(new Date(),
    // ex.getMessage(), request.getDescription(false));
    // return new ResponseEntity<Object>(resp,
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundExceptionExceptions(Exception ex, WebRequest request) {
        ExceptionResponse resp = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<Object>(resp, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String details = "";
        ExceptionResponse resp = new ExceptionResponse(new Date(), "Validation failed", ex.getBindingResult().getAllErrors().toString());
        return new ResponseEntity<Object>(resp, status);
    }
}
