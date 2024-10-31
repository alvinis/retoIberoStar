package com.jencys.iberostar.app.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggingAspectTest {

    LoggingAspect loggingAspect;

    @BeforeEach
    void setUp() {
        loggingAspect = new LoggingAspect();
    }

    @Test
    void logNegativeIdForGet() {
        loggingAspect.logNegativeIdForGet(1L);
    }

    @Test
    void logNegativeIdForGet_negative_id() {
        loggingAspect.logNegativeIdForGet(-1L);
    }

    @Test
    void logNegativeIdForUpdate() {
        loggingAspect.logNegativeIdForUpdate(1L, new Object());
    }

    @Test
    void logNegativeIdForDelete() {
        loggingAspect.logNegativeIdForDelete(1L);
    }
}