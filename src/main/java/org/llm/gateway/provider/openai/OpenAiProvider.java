package org.llm.gateway.provider.openai;

import org.llm.gateway.dto.ChatCompletionRequest;
import org.llm.gateway.dto.ChatCompletionResponse;
import org.llm.gateway.provider.LlmProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class OpenAiProvider implements LlmProvider {

    private final WebClient.Builder webClientBuilder;

    @Value("${providers.openai.api-key:dummy-key}")
    private String apiKey;

    @Value("${providers.openai.base-url:https://api.openai.com/v1}")
    private String baseUrl;

    public OpenAiProvider(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<ChatCompletionResponse> complete(ChatCompletionRequest request) {
        return webClientBuilder.baseUrl(baseUrl).build()
                .post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatCompletionResponse.class);
    }

    @Override
    public Flux<ChatCompletionResponse> stream(ChatCompletionRequest request) {
        return webClientBuilder.baseUrl(baseUrl).build()
                .post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(ChatCompletionResponse.class);
    }

    @Override
    public boolean supports(String providerName) {
        return "openai".equalsIgnoreCase(providerName);
    }

    @Override
    public String getName() {
        return "openai";
    }
}
