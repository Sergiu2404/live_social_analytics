package com.server.social_platform_server.config.rate_limiter;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterFilterConfig {
    @Bean
    public FilterRegistrationBean<RateLimiterFilter> rateLimiterFilter(){
        FilterRegistrationBean<RateLimiterFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RateLimiterFilter());
        registrationBean.addUrlPatterns("/api/auth/login");

        return registrationBean;
    }
}
