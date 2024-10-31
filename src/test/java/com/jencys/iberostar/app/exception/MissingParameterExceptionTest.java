package com.jencys.iberostar.app.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MissingParameterExceptionTest {
    @Test
    void test_constructor() {
        //act
        MissingParameterException result = new MissingParameterException("any-parameter");

        //asserts
        assertNotNull(result);
        assertNotNull(result.getMessage());
        assertEquals("El parámetro 'any-parameter' es obligatorio.", result.getMessage());
    }
}