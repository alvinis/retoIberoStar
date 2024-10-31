package com.jencys.iberostar.app.presentation.controller;

import com.jencys.iberostar.app.exception.MissingParameterException;
import com.jencys.iberostar.app.presentation.dto.ErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("Entidad no encontrada."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("Errores de validación: ");

        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMessage.append(error.getField())
                    .append(" ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(errorMessage.toString()));
    }

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<ErrorDTO> handleMissingParameterExceptions(MissingParameterException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(String.format("unexpected error: %s", ex.getMessage())));
    }
}
