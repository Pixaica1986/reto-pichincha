package com.banco.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://gorest.co.in/public/v2/users") // Base URL para el servicio REST externo
                .defaultHeader("Content-Type", "application/json") // Configura headers por defecto
                .build();
    }
}
