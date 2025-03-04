package com.example.userDataStore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Adjust this to match your API endpoints
                        .allowedOrigins("http://127.0.0.1:5500") // Allow your frontend
                        //.allowedOrigins("http://localhost:5500") // If using Live Server
                        //.allowedOrigins("http://localhost:8080") // If frontend is hosted elsewhere
                        //.allowedOrigins("https://aliasefi.github.io")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true);
            }
        };
    }
}
