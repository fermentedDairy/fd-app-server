package org.fermented.dairy.app.server.bootstrap.server.exceptions;

import java.util.Map;

/// Base exception class for errors occurring during the bootstrapping process.
///
/// This class is sealed and serves as the root for all bootstrapping-related exceptions.
///
/// @since 1.0.0
public sealed class BootstrapException extends Exception {

    protected BootstrapException(String message) {
        super(message);
    }

    /// Exception thrown when duplicate component names are found during bootstrapping.
    ///
    /// @since 1.0.0
    public static final class DuplicateComponentException extends BootstrapException{

        /// Creates a new instance of {@code DuplicateComponentException} with the mapping of duplicate names.
        ///
        /// @param duplicateComponentNames A map where keys are the component names and values are the reasons for duplication.
        /// @since 1.0.0
        public DuplicateComponentException(Map<String, String> duplicateComponentNames) {
            super("Duplicate component names found: " + duplicateComponentNames);
        }
    }
}
