package org.fermented.dairy.app.server.bootstrap.api;

import java.util.Map;
import java.util.function.Function;

/// Service Provider Interface (SPI) for bootstrapping fully initialised service components.
///
/// Implementing classes provide a fully initialised service component based on configuration.
/// This interface follows the Service Provider Interface (SPI) pattern, allowing multiple
/// implementations to be discovered via the service loader mechanism.
///
/// @since 1.0.0
public interface Bootstrapper extends Function<Map<String, String>, Bootstrapped> {

    /// Returns a fully initialised service component using the provided configuration.
    ///
    /// Implementing classes must ensure the returned component is fully initialised and ready for use.
    /// The configuration map contains key-value pairs that may be used for component initialization.
    ///
    /// @param config the configuration map containing initialization parameters
    /// @return a fully initialised service component
    /// @throws IllegalStateException if the component cannot be initialised with the given configuration
    /// @throws RuntimeException if an unexpected error occurs during initialisation
    /// @since 1.0.0
    Bootstrapped apply(Map<String, String> config);

    /// Returns the name of the bootstrapper.
    ///
    /// @return The name of this bootstrapper.
    /// @since 1.0.0
    String name();
}
