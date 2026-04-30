package org.fermented.dairy.app.server.concurrency.exceptions;

/// Base exception class for errors occurring within the threading module.
///
/// This class is sealed and serves as the root for all threading-related exceptions.
///
/// @since 1.0.0
public sealed class ThreadException extends Exception {
    /// Creates a new exception with the specified detail message.
    ///
    /// @param message The detail message.
    /// @since 1.0.0
    protected ThreadException(final String message){
        super(message);
    }

    /// Creates a new exception with the specified detail message and cause.
    ///
    /// @param message The detail and error message.
    /// @param cause The cause of the exception.
    /// @since 1.0.0
    protected ThreadException(final String message, Throwable cause){
        super(message, cause);
    }

    /// Exception thrown when a semaphore permit cannot be acquired.
    ///
    /// @since 1.0.0
    public static final class SemaphoreAcquisitionException extends ThreadException {
        /// Creates a new semaphore acquisition exception.
        ///
        /// @param message The detail message.
        /// @param cause The cause of the exception.
        /// @since 1.0.0
    /// Creates a new semaphore acquisition exception.
    ///
    /// @param message The detail message.
    /// @relies cause The cause of the exception.
    /// @since 1.0.0
    /// Creates a new semaphore acquisition exception.
    ///
    /// @param message The detail message.
    /// @param cause The cause of the exception.
    /// @since 1.0.0
    public SemaphoreAcquisitionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}


