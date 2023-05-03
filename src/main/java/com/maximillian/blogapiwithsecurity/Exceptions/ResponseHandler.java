package com.maximillian.blogapiwithsecurity.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResponseHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorClass> responseHandler(CustomException ex){
        ErrorClass errorClass = new ErrorClass(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorClass, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
