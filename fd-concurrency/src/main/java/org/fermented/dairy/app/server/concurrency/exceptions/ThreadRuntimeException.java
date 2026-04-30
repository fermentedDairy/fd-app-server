package org.fermented.dairy.app.server.concurrency.exceptions;

/// A runtime exception specifically for errors occurring during thread execution within the 
/// FD-Threads framework.
///
/// @since 1.0.0
public sealed class ThreadRuntimeException extends RuntimeException {
    /// Creates a new {@code ThreadRuntimeException} with the specified message.
    ///
    /// @param message The error message.
    /// @since 1.0.0
    protected ThreadRuntimeException(String message) {
        super(message);
    }

    /// Creates a new {@code ThreadRuntimeException} with the specified message and cause.
    ///
    /// @param message The error message.
    /// @param cause The underlying cause of the exception.
    /// @since 1.0.0
    protected ThreadRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /// An exception thrown when an error occurs during the execution of a task in an executor.
    ///
    /// @since 1.0.0
    public static final class ExecutionException extends ThreadRuntimeException {
        /// Creates a new instance of {@code ExecutionException} with the specified message and cause.
        ///
        /// @param message The error message.
        /// @param cause The underlying cause of the exception.
        /// @since 1.0.0
        public ExecutionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /// An exception thrown when the number of threads waiting for a semaphore permit 
    /// exceeds the allowed threshold.
    ///
    /// @since 1.0.0
    public static final class TooManyWaitingThreads extends ThreadRuntimeException {
        /// Creates a new instance of {@code TooManyWaitingThreads} with the current queue length.
        ///
        /// @param queueLength The number of threads currently waiting in the queue.
        /// @since 1.0.0
        public TooManyWaitingThreads(int queueLength) {
            super("Too many waiting jobs, waiting count: " + queueLength);
        }
    }
}
