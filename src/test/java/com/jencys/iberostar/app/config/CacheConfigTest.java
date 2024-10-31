package com.jencys.iberostar.app.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CacheConfigTest {

    CacheConfigProperty cacheConfigProperty;
    CacheConfig cacheConfig;

    @BeforeEach
    void setUp() {
        cacheConfigProperty = mock(CacheConfigProperty.class);
        cacheConfig = new CacheConfig(cacheConfigProperty);
    }

    @Test
    void given_a_valid_cache_manager_will_return_ok() {
        //arrange
        when(cacheConfigProperty.getCachesUsersInfoTtl()).thenReturn(1L);
        when(cacheConfigProperty.getCacheUsersInfoMaxSize()).thenReturn(1L);

        //act

        CacheManager result = cacheConfig.cacheManager();

        //asserts
        assertNotNull(result);
    }
}