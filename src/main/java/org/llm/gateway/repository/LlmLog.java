package org.llm.gateway.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("llm_logs")
public class LlmLog {
    @Id
    private Long id;
    private String provider;
    private String model;
    private String promptHash;
    private Integer promptTokens;
    private Integer completionTokens;
    private Long latencyMs;
    private LocalDateTime createdAt;
}
