package org.llm.gateway.dto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChatCompletionRequest {
    private String model;
    private List<ChatMessage> messages;
    private Boolean stream;
    private Double temperature;
    private Integer maxTokens;
    private Map<String, Object> metadata;

    public ChatCompletionRequest() {}

    public ChatCompletionRequest(String model, List<ChatMessage> messages, Boolean stream, Double temperature, Integer maxTokens, Map<String, Object> metadata) {
        this.model = model;
        this.messages = messages;
        this.stream = stream;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
        this.metadata = metadata;
    }

    public static ChatCompletionRequestBuilder builder() {
        return new ChatCompletionRequestBuilder();
    }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public List<ChatMessage> getMessages() { return messages; }
    public void setMessages(List<ChatMessage> messages) { this.messages = messages; }

    public Boolean getStream() { return stream; }
    public void setStream(Boolean stream) { this.stream = stream; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Integer getMaxTokens() { return maxTokens; }
    public void setMaxTokens(Integer maxTokens) { this.maxTokens = maxTokens; }

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatCompletionRequest that = (ChatCompletionRequest) o;
        return Objects.equals(model, that.model) && Objects.equals(messages, that.messages) && Objects.equals(stream, that.stream);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, messages, stream);
    }

    public static class ChatCompletionRequestBuilder {
        private String model;
        private List<ChatMessage> messages;
        private Boolean stream;
        private Double temperature;
        private Integer maxTokens;
        private Map<String, Object> metadata;

        ChatCompletionRequestBuilder() {}

        public ChatCompletionRequestBuilder model(String model) { this.model = model; return this; }
        public ChatCompletionRequestBuilder messages(List<ChatMessage> messages) { this.messages = messages; return this; }
        public ChatCompletionRequestBuilder stream(Boolean stream) { this.stream = stream; return this; }
        public ChatCompletionRequestBuilder temperature(Double temperature) { this.temperature = temperature; return this; }
        public ChatCompletionRequestBuilder maxTokens(Integer maxTokens) { this.maxTokens = maxTokens; return this; }
        public ChatCompletionRequestBuilder metadata(Map<String, Object> metadata) { this.metadata = metadata; return this; }

        public ChatCompletionRequest build() {
            return new ChatCompletionRequest(model, messages, stream, temperature, maxTokens, metadata);
        }
    }
}
