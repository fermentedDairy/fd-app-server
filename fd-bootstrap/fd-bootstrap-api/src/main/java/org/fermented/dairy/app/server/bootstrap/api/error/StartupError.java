package org.fermented.dairy.app.server.bootstrap.api.error;

import org.fermented.dairy.app.server.bootstrap.api.Bootstrapped;

/// Represents an exception condition when a bootstrapped component failed to start.
///
/// This interface encapsulates a Throwable that was thrown during component startup,
/// such as an error during component initialization or configuration validation.
/// It provides access to the underlying exception through the {@code #get()} method.
///
/// @see Bootstrapped#error()
/// @since 1.0.0
@FunctionalInterface
public interface StartupError {

    /// Returns the underlying throwable that caused startup failure.
    /// This throwable indicates the root cause of why a component failed to start
    /// (the `start()` method returned false). The throwable is typically used for logging,
    /// error reporting, or custom handler logic to determine appropriate recovery
    /// or fallback behavior.
    ///
    /// @return the exception or error that occurred; never returns null
    /// @since 1.0.0
    Throwable get();
}
