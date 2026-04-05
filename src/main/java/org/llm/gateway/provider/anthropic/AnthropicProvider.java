package org.llm.gateway.provider.anthropic;

import org.llm.gateway.dto.ChatCompletionRequest;
import org.llm.gateway.dto.ChatCompletionResponse;
import org.llm.gateway.provider.LlmProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class AnthropicProvider implements LlmProvider {

    private final WebClient.Builder webClientBuilder;

    @Value("${providers.anthropic.api-key:dummy-key}")
    private String apiKey;

    @Value("${providers.anthropic.base-url:https://api.anthropic.com/v1}")
    private String baseUrl;

    public AnthropicProvider(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<ChatCompletionResponse> complete(ChatCompletionRequest request) {
        return webClientBuilder.baseUrl(baseUrl).build()
                .post()
                .uri("/messages")
                .header("x-api-key", apiKey)
                .header("anthropic-version", "2023-06-01")
                .bodyValue(mapToAnthropic(request))
                .retrieve()
                .bodyToMono(ChatCompletionResponse.class);
    }

    @Override
    public Flux<ChatCompletionResponse> stream(ChatCompletionRequest request) {
        return webClientBuilder.baseUrl(baseUrl).build()
                .post()
                .uri("/messages")
                .header("x-api-key", apiKey)
                .header("anthropic-version", "2023-06-01")
                .bodyValue(mapToAnthropic(request))
                .retrieve()
                .bodyToFlux(ChatCompletionResponse.class);
    }

    private Map<String, Object> mapToAnthropic(ChatCompletionRequest request) {
        return Map.of(
            "model", request.getModel(),
            "messages", request.getMessages(),
            "max_tokens", request.getMaxTokens() != null ? request.getMaxTokens() : 1024,
            "stream", request.getStream() != null && request.getStream()
        );
    }

    @Override
    public boolean supports(String providerName) {
        return "anthropic".equalsIgnoreCase(providerName) || "claude".equalsIgnoreCase(providerName);
    }

    @Override
    public String getName() {
        return "anthropic";
    }
}
