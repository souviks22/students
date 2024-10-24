package com.rssoftware.students.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<WebError> runtimeExceptionHandler(Exception exception, WebRequest webRequest) {
        WebError webError = new WebError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(webError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebError> exceptionHandler(Exception exception, WebRequest webRequest) {
        WebError webError = new WebError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(webError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
