package org.fermented.dairy.app.server.config;

import java.util.Optional;

/// Interface for providing configuration values from various sources.
///
/// @since 1.0.0
public interface ConfigProvider {

    /// Retrieves the value associated with the specified key.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the configuration value if found, otherwise an empty [Optional].
    Optional<String> getValue(String key);

    /// Gets the priority of this configuration provider.
    ///
    /// Higher priority values take precedence during configuration lookup.
    ///
    /// @return The priority level of the provider.
    int getPriority();
}


