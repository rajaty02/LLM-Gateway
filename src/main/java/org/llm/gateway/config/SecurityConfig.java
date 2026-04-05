package org.llm.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
public class SecurityConfig {

    @Bean
    public WebFilter apiKeyValidationFilter() {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            
            // Bypass security for health check and actuator endpoints
            if (path.contains("/health") || path.contains("/actuator")) {
                return chain.filter(exchange);
            }

            String apiKey = exchange.getRequest().getHeaders().getFirst("X-API-KEY");
            
            // Simple validation: API key must start with 'sk-'
            if (apiKey == null || !apiKey.startsWith("sk-")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            
            return chain.filter(exchange);
        };
    }
}
