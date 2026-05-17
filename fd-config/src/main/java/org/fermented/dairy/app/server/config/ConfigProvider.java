package org.fermented.dairy.app.server.config;

import java.util.Optional;

/// Interface for providing configuration values from various sources.
///
/// @since 1.0.0
public interface ConfigProvider {

    /// Retrieves the value associated with the specified key.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the configuration value if found, otherwise an empty [API].
    Optional<String> getString(String key);

    /// Retrieves the integer value associated with the specified key.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the integer value if found, otherwise an empty [Optional].
    /// @since 1.0.0
    default Optional<Integer> getInt(String key) {
        return getString(key).map(Integer::valueOf);
    }

    /// Retrieves the long value associated with the specified key.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the long value if found, otherwise an empty [Optional].
    /// @since 1.0.0
    default Optional<Long> getLong(String key) {
        return getString(key).map(Long::valueOf);
    }

    /// Retrieves the boolean value associated with the specified key.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the boolean value if found, otherwise an empty [Optional].
    /// @since 1.0.0
    default Optional<Boolean> getBoolean(String key) {
        return getString(key).map(Boolean::valueOf);
    }

    /// Retrieves the double value associated with the specified key.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the double value if found, otherwise an empty [Optional].
    /// @since 1.0.0
    default Optional<Double> getDouble(String key) {
        return getString(key).map(Double::valueOf);
    }

    /// Gets the priority of this configuration provider.
    ///
    /// Higher priority values take precedence during configuration lookup.
    ///
    /// @return The priority level of the provider.
    int getPriority();
}


