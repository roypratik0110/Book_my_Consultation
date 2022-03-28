package com.upgrad.doctorservice.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class SpringCacheConfig {

    public final static String CACHE_ONE = "cache_one";

    @Bean
    public CacheBuilder<Object, Object> cacheBuilder(){
        return CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(5, TimeUnit.SECONDS);
    }

    @Bean
    public CacheManager cacheManager(CacheBuilder<Object,Object> cacheBuilder){
        GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
        guavaCacheManager.setCacheBuilder(cacheBuilder);
        return guavaCacheManager;
    }
}
