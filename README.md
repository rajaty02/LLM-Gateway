# LLM Gateway (Production-Grade Java Spring Boot WebFlux)

A high-performance, reactive LLM Gateway similar to LiteLLM, built using Java 21 and Spring Boot 3. It provides a unified API to route requests to multiple LLM providers with built-in observability, resilience, and caching.

## Features

- **Unified API Gateway**: Single endpoint `POST /v1/chat/completions` for OpenAI, Anthropic, Azure, and Mistral.
- **Reactive Stack**: Built with Spring WebFlux and Project Reactor for non-blocking, high-concurrency request handling.
- **Streaming Support**: Real-time streaming responses via Server-Sent Events (SSE).
- **Strategy Pattern**: Modular provider implementation for easy extensibility.
- **Resilience**:
  - Circuit Breaker (Resilience4j) to prevent cascading failures.
  - Retry mechanism for transient errors.
  - Timeout handling using WebClient.
- **Observability**:
  - Request/Response logging to PostgreSQL.
  - Async event logging to Kafka for downstream analysis.
  - Token usage tracking.
- **Caching**: Redis-based caching to reduce latency and costs for duplicate prompts.
- **Security**:
  - API Key validation middleware.
  - Rate limiting per API Key.

## Tech Stack

- **Java 21** & **Spring Boot 3.2.4**
- **Spring WebFlux** (Reactive Streams)
- **Redis** (Reactive Redis Template)
- **PostgreSQL** (R2DBC for reactive DB access)
- **Apache Kafka** (Async Logging)
- **Resilience4j** (Circuit Breaker & Retry)
- **Lombok** & **Maven**

## Project Structure

```text
src/main/java/org/llm/gateway/
├── config/         # Configuration for WebClient, Redis, Kafka, Security
├── controller/     # REST Controllers for Unified API
├── dto/            # Data Transfer Objects (OpenAI compatible)
├── provider/       # Strategy implementations for LLM Providers (OpenAI, Anthropic, etc.)
├── repository/     # R2DBC repositories for PostgreSQL logging
├── service/        # Core business logic for routing, caching, and resilience
└── util/           # Utility classes for hashing and mapping
```

## Getting Started

1. **Prerequisites**:
   - Java 21
   - Redis
   - PostgreSQL
   - Kafka

2. **Configuration**:
   Update `src/main/resources/application.yml` with your API keys or set environment variables:
   - `OPENAI_API_KEY`
   - `ANTHROPIC_API_KEY`

3. **Run Application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Sample Request**:
   ```bash
   curl -X POST http://localhost:8080/v1/chat/completions \
     -H "Content-Type: application/json" \
     -H "X-Provider: openai" \
     -d '{
       "model": "gpt-4",
       "messages": [{"role": "user", "content": "Hello!"}],
       "stream": false
     }'
   ```

## Author
Rajat - [GitHub Profile](https://github.com/your-username)
