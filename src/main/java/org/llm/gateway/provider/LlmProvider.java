package org.llm.gateway.provider;

import org.llm.gateway.dto.ChatCompletionRequest;
import org.llm.gateway.dto.ChatCompletionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LlmProvider {
    Mono<ChatCompletionResponse> complete(ChatCompletionRequest request);
    Flux<ChatCompletionResponse> stream(ChatCompletionRequest request);
    boolean supports(String providerName);
    String getName();
}
