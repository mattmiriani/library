package com.api.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsConfig {

    private final UrlBasedCorsConfigurationSource corsConfigurationSource;

    public CorsConfig() {
        this.corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowedHeaders(List.of("Origin", "X-Requested-With", "Accept", "Content-Type", "authorization", "Cache-Control"));
        config.setMaxAge(3600L);

        this.corsConfigurationSource.registerCorsConfiguration("/**", config);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return corsConfigurationSource;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource);
    }
}
