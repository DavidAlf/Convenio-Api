package com.proyecto.convenios.controller.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import co.com.ath.commons.dto.api.AbstractResponseApi;
import co.com.ath.commons.dto.convenios.api.ResPostConvenio;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AbstractResponseApi> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errorMessages.add(error.getField() + ": " + error.getDefaultMessage()));
        
        ResPostConvenio response = (ResPostConvenio) AbstractResponseApi.validationError(ResPostConvenio.class,
                String.join(", ", errorMessages));
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
