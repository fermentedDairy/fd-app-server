# OpenCode Agent Instructions

## Investigation Strategy

**Read Highest-Value Sources First:**
- `pom.xml` and `Makefile` are the primary sources for build/test/lint commands.
- Source modules: `fd-bootstrap/`, `fd-http/`, `fd-file-server/`, `fd-threads/`.

**Executable Sources of Truth:**
- **Build:** Use Maven via `./mvnw` or `mvnw.cmd`.
  - `mvnw clean install` runs a full build and local install.
  - `mvnw clean package -DskipTests` builds the project package, skipping tests.
- **Test:** The primary command for running unit tests is `mvn clean compile test`.
- **Code/Config:** `pom.xml` confirms this is a multi-module Maven project using Java 25.

## Required Commands & Workflow

- **Build & Test:** Use `mvn clean compile test` for testing and `mvn clean install` for a full build/install.
- **Initial Setup:** If dependencies are missing, `mvn install` is necessary.

## Conventions & Gotchas

- **Build Tool:** Maven is the project's standard build tool.
- **Testing:** The test invocation is `mvn clean compile test`.
- **Documentation:** Use the `documenter` subagent to write JavaDoc documentation for interfaces and classes. Specify requirements including markdown Javadoc format (///), `@FunctionalInterface` annotations, and `@since` version tags.
- **Imports:** Avoid wildcard imports; use fully qualified type names and explicit imports only.
