# fd-config

`fd-config` is a lightweight configuration management library for the Fermented Dairy application server. It allows for aggregating configuration values from multiple sources (e.g., system properties, properties files) using a prioritized provider model.

## Features

- **Provider-based architecture**: Implement the `ConfigProvider` interface to load configurations from any source.
- **Priority-based lookup**: Config values are retrieved by searching through providers in order of their priority. The first provider to return a value for a given key wins.
- **Extensible**: New configuration sources can be added easily via the Java `ServiceLoader` mechanism.

## Important Note

This project is a dependency for the `fd-bootstrap` project and **cannot be bootstrapped**. It must be built and installed using Maven before `fd-bootstrap` can be used.

## Usage

### Basic Usage

The `AppConfig` class is the primary entry point. When instantiated with no arguments, it automatically loads all available `ConfigProvider`s found via `ServiceLoader`.

```java
import org.fermented.dairy.app.server.config.Config;

import java.util.Optional;

public class Example {
    public static void main(String[] args) {
        Config config = new Config();

        Optional<String> dbUrl = config.getString("db.url");
        dbUrl.ifPresent(url -> System.out.println("Database URL: " + url));
    }
}
```

### Implementing a Custom ConfigProvider

To add a new configuration source, implement the `ConfigProvider` interface and register it using the `ServiceLoader` (e.g., by adding it to `META-INF/services/org.fermented.dairy.app.server.config.ConfigProvider`).

```java
import org.fermented.dairy.app.server.config.ConfigProvider;
import java.util.Optional;

public class MyCustomProvider implements ConfigProvider {

    @Override
    public Optional<String> getValue(String key) {
        if ("my.key".equals(key)) {
            return Optional.of("my-value");
        }
        return Optional.empty();
    }

    @Override
    public int getPriority() {
        // Lower priority means it will be loaded first
        return 100;
    }
}
```
