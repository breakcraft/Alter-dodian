# Alter-dodian Roadmap

This document consolidates production-oriented tasks for Alter-dodian. Each sprint focuses on a single deliverable with a clear Definition of Done (DoD).

## 1. Observability & Diagnostics

### 1.1 Unified Metrics
- Add Micrometer Prometheus registry to the game-server Gradle module and expose `/prometheus-metrics` using Ktor's snippet.
- **DoD:** Prometheus scraping works locally and a Grafana dashboard is stored under `docs/grafana/alter.json`.

### 1.2 Structured Logging
- Replace raw SLF4J with kotlin-logging and use `withLoggingContext` so each log line carries `sessionId` and `pluginId` MDC keys.
- Emit JSON with `logstash-logback-encoder` so logs can be shipped to Loki.
- **DoD:** Kibana search for `pluginId:"bank"` returns results in under two seconds.

## 2. Persistent Data Layer

### 2.1 PostgreSQL + Exposed
- Add a PostgreSQL service in `docker-compose.yml` using HikariCP for connections.
- Model `players`, `inventories`, and `bans` tables with JetBrains Exposed.
- Handle schema migrations with Flyway and run `flywayMigrate` before tests.
- **DoD:** Creating a character inserts a row into `players` and Flyway checksums pass.

### 2.2 Integration Tests
- Use Testcontainers PostgreSQL for repository tests with startup-to-green time under 10 seconds.
- **DoD:** Builds fail if an integration test uses `@Transactional` without committing.

## 3. Deployment & Runtime Ops

### 3.1 Slim Docker Images
- Produce a multi-stage Dockerfile. Stage 1 builds; stage 2 runs a JLink image under 120 MB.
- `.dockerignore` excludes `data/cache` and local Gradle caches.

### 3.2 Helm Chart
- Provide `/deploy/helm/alter` with a StatefulSet for `game-server` and a Deployment for `http-api`.
- Include `ServiceMonitor` and default HPA rules using CPU and `active_players` metrics.
- **DoD:** `helm template .` lints without errors and `kubectl get hpa game-server` shows metrics.

## 4. Scalability & Performance
- Move long-running loops such as path-finding and region ticks to Kotlin coroutines (`Dispatchers.Default` for CPU work, `Dispatchers.IO` for blocking I/O).
- Add a `benchmarks` module with kotlinx-benchmark/JMH.
- Capture profiles using Java Flight Recorder with templates stored under `benchmarks/`.
- **DoD:** 1,000 dummy players keep the main tick under 600 µs p95 on a 4-core VM.

## 5. Security & Public APIs
- Protect `/api/**` with JWT (`HS256` in development, `RS256` via Vault in production).
- Rate-limit `/auth/login` using Bucket4j at 5 requests per second per IP.
- **DoD:** Exceeding the rate limit returns HTTP 429 with `Retry-After`.

## 6. Plugin Ecosystem & Hot Reload
- Load each plugin JAR with a dedicated `URLClassLoader`; clean up via `Plugin.onUnload()` then `classLoader.close()`.
- Consider `hotswap-classloader` (or equivalent) to accelerate development loops.
- Provide a `plugin-archetype` under `buildSrc` so contributors can run `./gradlew :myPlugin:runGame`.
- **DoD:** Hitting `/api/plugins/reload?id=teleports` updates commands without restarting the server.

## 7. CI / CD & Quality Gates
- GitHub Actions matrix builds, runs tests, pushes Docker images, and runs a Helm dry run.
- Add a SonarCloud analysis step and require the quality gate to pass.
- Enable Dependabot for Gradle and Docker updates.
- **DoD:** A failing SonarCloud gate blocks `main` and Dependabot PRs auto-trigger the pipeline.

## 8. Suggested Sprint Order

| Sprint | Deliverable (DoD)                               | Core Files                                  |
|-------:|-----------------------------------------------|---------------------------------------------|
| 1      | `/prometheus-metrics` endpoint + dashboard    | `MetricsModule.kt`, `docs/grafana/`         |
| 2      | JSON structured logging to Loki               | `logback.xml`, `Logging.kt`                 |
| 3      | PostgreSQL persistence for players            | `docker-compose.yml`, `PlayerDao.kt`        |
| 4      | CI matrix + Testcontainers                    | `.github/workflows/ci.yml`                  |
| 5      | Plugin hot-reload MVP                         | `PluginManager.kt`                          |

Keep this roadmap updated as sprints are completed.
