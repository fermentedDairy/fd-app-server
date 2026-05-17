package org.fermented.dairy.app.server.config;

import java.util.Optional;

/// A configuration provider that looks up values from Java system properties.
///
/// @since 1.0.0
public class SystemPropertiesConfigProvider implements ConfigProvider {

    /// Retrieves a value from the system properties.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the configuration value if found, otherwise an empty [Optional].
    @Override
    public Optional<String> getString(String key) {
        return Optional.ofNullable(System.getProperty(key));
    }

    /// Gets the priority of this provider.
    ///
    ///Higher priority values take precedence during configuration lookup.
    ///
    /// @return The highest possible priority level.
    @Override
    public int getPriority() {
        return Integer.MAX_VALUE; // Higher priority (last)
    }
}
