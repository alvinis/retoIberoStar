package com.jencys.iberostar.app.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cache-config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheConfigProperty {
    private long cachesUsersInfoTtl;
    private long cacheUsersInfoMaxSize;
}
