package org.llm.gateway.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.llm.gateway.dto.ChatCompletionRequest;
import org.llm.gateway.dto.ChatCompletionResponse;
import org.llm.gateway.provider.LlmProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class LlmService {

    private static final Logger log = LoggerFactory.getLogger(LlmService.class);

    private final List<LlmProvider> providers;
    private final ReactiveRedisTemplate<String, Object> redisTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public LlmService(List<LlmProvider> providers, 
                      ReactiveRedisTemplate<String, Object> redisTemplate, 
                      KafkaTemplate<String, Object> kafkaTemplate) {
        this.providers = providers;
        this.redisTemplate = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @CircuitBreaker(name = "llmCircuitBreaker", fallbackMethod = "fallbackCompletion")
    @Retry(name = "llmRetry")
    public Mono<ChatCompletionResponse> getCompletion(String providerName, ChatCompletionRequest request) {
        String cacheKey = generateCacheKey(request);

        return redisTemplate.opsForValue().get(cacheKey)
                .cast(ChatCompletionResponse.class)
                .doOnNext(cached -> log.info("Cache hit for request"))
                .switchIfEmpty(
                    findProvider(providerName)
                        .complete(request)
                        .flatMap(response -> 
                            redisTemplate.opsForValue().set(cacheKey, response, Duration.ofHours(1))
                                .thenReturn(response)
                        )
                )
                .doOnSuccess(response -> logToKafka("llm-logs", response));
    }

    public Flux<ChatCompletionResponse> getStream(String providerName, ChatCompletionRequest request) {
        return findProvider(providerName)
                .stream(request)
                .doOnNext(response -> logToKafka("llm-stream-logs", response));
    }

    private LlmProvider findProvider(String providerName) {
        return providers.stream()
                .filter(p -> p.supports(providerName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported provider: " + providerName));
    }

    private String generateCacheKey(ChatCompletionRequest request) {
        return "llm:cache:" + request.getModel() + ":" + request.getMessages().hashCode();
    }

    private void logToKafka(String topic, Object message) {
        kafkaTemplate.send(topic, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) log.error("Failed to send log to Kafka", ex);
                });
    }

    public Mono<ChatCompletionResponse> fallbackCompletion(String provider, ChatCompletionRequest request, Throwable t) {
        log.error("Fallback triggered for provider {} due to: {}", provider, t.getMessage());
        return Mono.error(new RuntimeException("Service temporarily unavailable, please try again later."));
    }
}
