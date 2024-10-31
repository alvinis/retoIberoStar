package com.jencys.iberostar.app.presentation.controller;

import com.jencys.iberostar.app.exception.MissingParameterException;
import com.jencys.iberostar.app.presentation.dto.ErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void test_handleEntityNotFound() {
        // Arrange
        EntityNotFoundException entityNotFoundException = new EntityNotFoundException();

        // Act
        ResponseEntity<ErrorDTO> result = globalExceptionHandler.handleEntityNotFound(entityNotFoundException);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Entidad no encontrada.", result.getBody().getMessage());
    }

    @Test
    void test_handleValidationExceptions() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        FieldError fieldError1 = new FieldError("objectName", "field1", "must not be null");
        FieldError fieldError2 = new FieldError("objectName", "field2", "must be a valid email");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        // Act
        ResponseEntity<ErrorDTO> result = globalExceptionHandler.handleValidationExceptions(exception);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertTrue(result.getBody().getMessage().contains("Errores de validación: field1 must not be null; field2 must be a valid email;"));
    }

    @Test
    void test_handleMissingParameterExceptions() {
        // Arrange
        MissingParameterException missingParameterException = new MissingParameterException("Missing parameter");

        // Act
        ResponseEntity<ErrorDTO> result = globalExceptionHandler.handleMissingParameterExceptions(missingParameterException);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("El parámetro 'Missing parameter' es obligatorio.", result.getBody().getMessage());
    }

    @Test
    void test_handleException() {
        // Arrange
        Exception exception = new Exception("General error");

        // Act
        ResponseEntity<ErrorDTO> result = globalExceptionHandler.handleException(exception);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("unexpected error: General error", result.getBody().getMessage());
    }
}