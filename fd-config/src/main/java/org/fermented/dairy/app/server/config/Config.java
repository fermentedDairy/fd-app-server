package org.fermented.dairy.app.server.config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.BiFunction;

import static org.fermented.dairy.app.server.config.logging.Logging.LOGGER;

/// Main configuration manager that aggregates multiple configuration providers.
///
/// This class manages a list of [ConfigProvider]s, searched in order of their
/// priority. The first provider to return a value for a given key wins.
///
/// @since 1.0.0
public class Config {

    private static final BiFunction<ConfigProvider, String, Optional<Double>> DOUBLE_MAPPER = ConfigProvider::getDouble;
    private static final BiFunction<ConfigProvider, String, Optional<Long>> LONG_MAPPER = ConfigProvider::getLong;
    private static final BiFunction<ConfigProvider, String, Optional<Boolean>> BOOLEAN_MAPPER = ConfigProvider::getBoolean;
    private static final BiFunction<ConfigProvider, String, Optional<String>> STRING_MAPPER = ConfigProvider::getString;
    private static final BiFunction<ConfigProvider, String, Optional<Integer>> INTEGER_MAPPER = ConfigProvider::getInt;

    private final List<ConfigProvider> providers;


    /// Initializes a new [Config] by loading all [ConfigProvider]s
    /// available via [ServiceLoader].
    ///
    /// @since 1.0.0
    public Config() {
        this.providers = new ArrayList<>();
        ServiceLoader<ConfigProvider> loader = ServiceLoader.load(ConfigProvider.class);
        for (ConfigProvider provider : loader) {
            providers.add(provider);
        }
        providers.sort(Comparator.comparingInt(ConfigProvider::getPriority));
    }

    /// Creates an [Config] with a specific list of providers.
    ///
    /// @param providers The list of [ConfigProvider]s to use.
    /// @since 1.0.0
    public Config(List<ConfigProvider> providers) {
        this.providers = new ArrayList<>(providers);
        this.providers.sort(Comparator.comparingInt(ConfigProvider::getPriority));
    }

    /// Retrieves a configuration value from the first provider that contains it.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the configuration value if found, otherwise an empty [Optional].
    /// @since 1.0.0
    public Optional<String> getString(String key) {
        var foundValue = iterateProviders(key, STRING_MAPPER);
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

    /// Retrieves an integer value from the first provider that contains it.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the integer value if found, otherwise an empty [Optional].
    /// @since 1.0.0
    public Optional<Integer> getInt(String key) {
        return iterateProviders(key, INTEGER_MAPPER);
    }

    /// Retrieves an integer value for the given key, returning the [defaultValue] if the key is not found.
    ///
    /// @param key The configuration key to look up.
    /// @param defaultValue The value to return if the key is not found.
    /// @return The configuration value if found, otherwise [defaultValue].
    /// @since 1.0.0
    public int getInt(String key, int defaultValue) {
        return getInt(key).orElse(defaultValue);
    }

    /// Retrieves a long value from the first provider that contains it.
    ///
    /// @param key The configuration and key to look up.
    /// @return An [Optional] containing the long value if found, otherwise an empty [Optional].
    /// @since 1.0.0
    public Optional<Long> getLong(String key) {
        return iterateProviders(key, LONG_MAPPER);
    }

    /// Retrieves a long value for the given key, returning the [defaultValue] if the key is not found.
    ///
    /// @param key The configuration key to look up.
    /// @param defaultValue The value to return if the key is not found.
    /// @return The configuration value if found, otherwise [defaultValue].
    /// @since 1.0.0
    public long getLong(String key, long defaultValue) {
        return getLong(key).orElse(defaultValue);
    }

    /// Retrieves a boolean value from the first provider that contains it.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the boolean value if found, otherwise an empty [Optional].
    /// @since 1.0.0
    public Optional<Boolean> getBoolean(String key) {
        return iterateProviders(key, BOOLEAN_MAPPER);
    }

    /// Retrieves a boolean value for the given key, returning the [defaultValue] if the key is not found.
    ///
    /// @param key The configuration key to look up.
    /// @param defaultValue The value to return if the key is not found.
    /// @return The configuration value if found, otherwise [defaultValue].
    /// @since 1.0.0
    public boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(key).orElse(defaultValue);
    }

    /// Retrieves a double value from the first provider that contains it.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the double value if found, otherwise an empty [Optional].
    /// @since 1.0.0
    public Optional<Double> getDouble(String key) {
        return iterateProviders(key, DOUBLE_MAPPER);
    }

    /// Retrieves a double value for the given key, returning the [defaultValue] if the key is not found.
    ///
    /// @param key The configuration key to look up.
    /// @param defaultValue The value to return if the key is not found.
    /// @return The configuration value if found, otherwise [defaultValue].
    /// @since 1.0.0
    public double getDouble(String key, double defaultValue) {
        return getDouble(key).orElse(defaultValue);
    }
    private <T> Optional<T> iterateProviders(String key, BiFunction<ConfigProvider, String, Optional<T>> mapper) {
        return providers.stream()
                .map(provider -> mapper.apply(provider, key))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}
