package com.groupeisi.tp_spring_boot.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "spring", name = "cache.type", havingValue = "redis")
@Configuration
public class RedisConfig {

}
