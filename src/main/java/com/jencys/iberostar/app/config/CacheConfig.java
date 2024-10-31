package com.jencys.iberostar.app.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {

    private final CacheConfigProperty cacheConfigProperty;

    public static final String SPACESHIP_INFO_CACHE = "SPACESHIP_INFO_CACHE";

    @Bean
    public CacheManager cacheManager(){
        List<CaffeineCache> caches = new ArrayList<>();
        caches.add(buildCache(cacheConfigProperty.getCachesUsersInfoTtl(), cacheConfigProperty.getCacheUsersInfoMaxSize()));

        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(caches);
        return manager;
    }

    private static CaffeineCache buildCache(long ttl, long size){
        return new CaffeineCache(CacheConfig.SPACESHIP_INFO_CACHE, Caffeine.newBuilder()
                .expireAfterWrite(ttl, TimeUnit.HOURS)
                .maximumSize(size)
                .build());
    }
}
