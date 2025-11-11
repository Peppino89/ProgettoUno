package com.example.progettouno.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;


import java.util.Optional;

@Configuration
@EnableMongoAuditing(auditorAwareRef = "auditorProvider")
public class MongoConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("system"); // o recupera da SecurityContextHolder
    }
}
