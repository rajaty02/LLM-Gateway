package org.llm.gateway.controller;

import org.llm.gateway.dto.ChatCompletionRequest;
import org.llm.gateway.dto.ChatCompletionResponse;
import org.llm.gateway.service.LlmService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/v1")
public class GatewayController {

    private final LlmService llmService;

    public GatewayController(LlmService llmService) {
        this.llmService = llmService;
    }

    @GetMapping("/health")
    public Mono<Map<String, String>> health() {
        return Mono.just(Map.of("status", "UP", "message", "LLM Gateway is running"));
    }

    @PostMapping(value = "/chat/completions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ChatCompletionResponse> completion(
            @RequestHeader(value = "X-Provider", defaultValue = "openai") String provider,
            @RequestBody ChatCompletionRequest request) {
        
        request.setStream(false);
        return llmService.getCompletion(provider, request);
    }

    @PostMapping(value = "/chat/completions", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ChatCompletionResponse> streamCompletion(
            @RequestHeader(value = "X-Provider", defaultValue = "openai") String provider,
            @RequestBody ChatCompletionRequest request) {
        
        request.setStream(true);
        return llmService.getStream(provider, request);
    }
}
