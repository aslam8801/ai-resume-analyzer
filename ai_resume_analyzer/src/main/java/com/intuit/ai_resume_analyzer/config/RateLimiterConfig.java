package com.intuit.ai_resume_analyzer.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfig {

    @Bean
    public RateLimiter rateLimiter(){

        io.github.resilience4j.ratelimiter.RateLimiterConfig config =
                io.github.resilience4j.ratelimiter.RateLimiterConfig.custom()
                        .limitForPeriod(5)
                        .limitRefreshPeriod(Duration.ofSeconds(1))
                        .timeoutDuration(Duration.ofMillis(0))
                        .build();

        return RateLimiter.of("geminiLimiter", config);

    }

}