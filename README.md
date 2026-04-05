# 🚀 LLM Gateway

### *Production-Grade Reactive AI Gateway (Java 21 + Spring Boot WebFlux)*

Welcome to **LLM Gateway** — your high-speed, battle-ready control tower for Large Language Models.
Think of it as a **universal translator + traffic controller + performance optimizer** for AI APIs.

Built with **Java 21** and **Spring Boot WebFlux**, this gateway doesn’t just route requests… it *orchestrates intelligence at scale* ⚡

---

## ✨ Why This Exists

Modern AI apps juggle multiple providers. Each has:

* Different APIs
* Different quirks
* Different failure patterns

This project solves that chaos with **one clean, unified interface**.

---

## 🧠 Core Capabilities

### 🔌 Unified LLM API

One endpoint to rule them all:

```http
POST /v1/chat/completions
```

Supports:

* OpenAI
* Anthropic
* Azure OpenAI
* Mistral

---

### ⚡ Reactive by Design

Powered by **Spring WebFlux + Project Reactor**

* Non-blocking I/O
* Handles massive concurrent requests effortlessly
* Built for real-world scale, not demos

---

### 🌊 Streaming Support (SSE)

Real-time responses via **Server-Sent Events**

* Token-by-token streaming
* Ideal for chat UIs & live AI feedback

---

### 🧩 Pluggable Strategy Pattern

Providers are modular:

* Easy to add new LLM vendors
* Clean separation of logic
* Zero chaos, maximum extensibility

---

### 🛡️ Resilience Layer (Production Armor)

* Circuit Breaker → prevents cascading failures
* Retry → handles transient API issues
* Timeout Control → no hanging requests

---

### 🔍 Observability (Know Everything)

* Request/Response logging → PostgreSQL
* Async event streaming → Kafka
* Token usage tracking → cost visibility

---

### ⚡ Smart Caching (Redis)

* Avoid duplicate LLM calls
* Reduce latency ⚡
* Save money 💰

---

### 🔐 Security Built-In

* API Key validation
* Rate limiting per key
* Gateway-level protection

---

## 🧰 Tech Stack

| Layer      | Technology         |
| ---------- | ------------------ |
| Language   | Java 21            |
| Framework  | Spring Boot 3.2    |
| Reactive   | Spring WebFlux     |
| Cache      | Redis              |
| Database   | PostgreSQL (R2DBC) |
| Streaming  | Apache Kafka       |
| Resilience | Resilience4j       |
| Build Tool | Maven              |

---

## 🗂️ Project Structure

```text
src/main/java/org/llm/gateway/
├── config/        ⚙️  WebClient, Redis, Kafka, Security configs
├── controller/    🎯  Unified REST endpoints
├── dto/           📦  OpenAI-compatible request/response models
├── provider/      🔌  LLM provider strategies (OpenAI, Anthropic, etc.)
├── repository/    🗄️  Reactive DB layer (PostgreSQL)
├── service/       🧠  Routing, caching, resilience logic
└── util/          🛠️  Helpers (hashing, mapping, etc.)
```

---

## ⚙️ Getting Started

### 1️⃣ Prerequisites

* Java 21
* Redis
* PostgreSQL
* Kafka

---

### 2️⃣ Configuration

Update:

```yaml
src/main/resources/application.yml
```

Or use environment variables:

```bash
OPENAI_API_KEY=
ANTHROPIC_API_KEY=
```

---

### 3️⃣ Run the App

```bash
mvn spring-boot:run
```

---

### 4️⃣ Sample Request

```bash
curl -X POST http://localhost:8080/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "X-Provider: openai" \
  -d '{
    "model": "gpt-4",
    "messages": [
      { "role": "user", "content": "Hello!" }
    ],
    "stream": false
  }'
```

---

## 🧪 What Makes This Resume-Worthy?

* ✅ Reactive architecture (not basic MVC)
* ✅ Multi-provider LLM abstraction
* ✅ Production-grade resilience patterns
* ✅ Real-time streaming support
* ✅ Observability + distributed logging
* ✅ Cost optimization via caching

This is not a toy project.
It’s the kind of system you’d expect in a **real AI platform backend**.

---

## 👨‍💻 Author

**Rajat**
🔗 https://github.com/your-username

---

## 🌟 Final Thought

This gateway is like a **neural switchboard** —
quietly routing intelligence, optimizing every request, and keeping your AI stack fast, stable, and scalable.

Plug it in. Scale it up. Let it run. ⚡

