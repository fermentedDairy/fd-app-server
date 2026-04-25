package org.fermented.dairy.app.server.bootstrap.api;

import org.fermented.dairy.app.server.bootstrap.api.error.StartupError;

import java.util.Optional;

/// Lifecycle interface for bootstrapped service components.
///
/// Provides a common contract for all components produced by the bootstrapper,
/// enabling uniform management of component lifecycle operations including start,
/// stop, and state monitoring.
///
/// @see Bootstrapper
/// @since 1.0.0
public interface Bootstrapped {

    /// Starts this component and attempts to bring it to a running state.
    ///
    /// The implementation should handle concurrent start attempts appropriately,
    /// ensuring the component is only started once.
    ///
    /// @return true if the start operation succeeded, false otherwise
    /// @since 1.0.0
    boolean start();

    /// Checks whether this component is currently in a running state.
    ///
    /// @return true if the component is running, false otherwise or if running state is unknown
    /// @since 1.0.0
    boolean started();

    /// Stops this component and attempts to bring it to a stopped state.
    ///
    /// The implementation should handle concurrent stop requests appropriately,
    /// ensuring the component is only stopped once.
    ///
    /// @return true if the stop operation succeeded, false otherwise
    /// @since 1.0.0
    boolean stop();

    /// Checks whether this component is currently in a stopped state.
    ///
    /// @return true if the component is stopped, false otherwise or if stopped state is unknown
    /// @since 1.0.0
    boolean stopped();

    /// Returns an optional error associated with this component's lifecycle state.
    ///
    /// This method provides an Optional containing the {@link StartupError} that explains
    /// why a component failed to start. If no error occurred, the Optional will be empty.
    /// The StartupError supplies access to the underlying exception through its
    /// {@link StartupError#get()} method.
    ///
    /// @return an Optional containing a StartupError if the component failed to start
    ///         or shut down, empty otherwise
    /// @since 1.0.0
    Optional<StartupError> error();
}
