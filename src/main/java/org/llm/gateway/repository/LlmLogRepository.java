package org.llm.gateway.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LlmLogRepository extends ReactiveCrudRepository<LlmLog, Long> {
}
