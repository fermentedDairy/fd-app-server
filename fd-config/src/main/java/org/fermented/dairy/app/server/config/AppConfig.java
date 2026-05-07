package org.fermented.dairy.app.server.config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import static org.fermented.dairy.app.server.config.logging.Logging.LOGGER;

/// Main configuration manager that aggregates multiple configuration providers.
///
/// This class manages a list of [ConfigProvider]s, searched in order of their
/// priority. The first provider to return a value for a given key wins.
///
/// @since 1.0.0
public class AppConfig {

    private final List<ConfigProvider> providers;

    /// Initializes a new [AppConfig] by loading all [ConfigProvider]s
    /// available via [ServiceLoader].
    ///
    /// @since 1.0.0
    public AppConfig() {
        this.providers = new ArrayList<>();
        ServiceLoader<ConfigProvider> loader = ServiceLoader.load(ConfigProvider.class);
        for (ConfigProvider provider : loader) {
            providers.add(provider);
        }
        providers.sort(Comparator.comparingInt(ConfigProvider::getPriority));
    }

    /// Creates an [AppConfig] with a specific list of providers.
    ///
    /// @param providers The list of [ConfigProvider]s to use.
    /// @since 1.0.0
    public AppConfig(List<ConfigProvider> providers) {
        this.providers = new ArrayList<>(providers);
        this.providers.sort(Comparator.comparingInt(ConfigProvider::getPriority));
    }

    /// Retrieves a configuration value from the first provider that contains it.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the configuration value if found, otherwise an empty [Optional].
    /// @since 1.0.0
    public Optional<String> getString(String key) {
        var foundValue = providers.stream()
                .map(provider -> provider.getValue(key))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
        if(LOGGER.isDebugEnabled()) {
            foundValue.ifPresentOrElse(
                    value -> LOGGER.debug("Configuration value for key: {} found, value: {}", key, value),
                    () -> LOGGER.debug("Configuration value for key: {} not found in any provider", key)
            );
        }
        return foundValue;
    }

    /// Retrieves a configuration value for the given key, returning the [defaultValue] if the key is not found.
    ///
    /// @param key The configuration key to look up.
    /// @param defaultValue The value to return if the key is not found.
    /// @return The configuration value if found, otherwise [defaultValue].
    /// @since 1.0.0
    public String getString(String key, String defaultValue) {
        return getString(key).orElse(defaultValue);
    }
}
