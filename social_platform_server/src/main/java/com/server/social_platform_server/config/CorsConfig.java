package com.server.social_platform_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); //allow cookies and credentials
        config.setAllowedOrigins(List.of("http://localhost:5173")); //client server
        config.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
