package com.jencys.iberostar.app.data.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceshipTest {

    @Test
    void test_constructor() {
        Spaceship spaceship = new Spaceship("any-name", "any-series", "any-movie");
        assertNotNull(spaceship);
    }
}