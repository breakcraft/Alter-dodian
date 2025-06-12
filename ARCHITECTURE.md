# Project Architecture

This document provides a high level overview of the repository layout and module relationships.

## Directory overview

| Path | Description |
| --- | --- |
| `game-server` | Core Kotlin game server implementation. Contains main runtime logic and unit tests. |
| `game-plugins` | Optional plugins that extend game functionality. Built as a separate Gradle module. |
| `game-api` | Public API used by plugins and other modules. |
| `net` | Networking layer used by the server. |
| `util` | Shared utility code across modules. |
| `http-api` | Vue.js based web management interface. |
| `data` | Configuration files such as `api.yml`, `blocks.yml`, and cache data. |
| `Dockerfile` / `docker-compose.yml` | Container configuration to run the server in Docker. |

The Gradle build in `build.gradle.kts` configures these modules and includes packaging tasks such as `packageServer` and `packageLibs`.

## Build

The project uses Gradle with the Kotlin DSL. Run `gradle build` to compile all modules. Tests are located in `game-server/src/test/kotlin`.

