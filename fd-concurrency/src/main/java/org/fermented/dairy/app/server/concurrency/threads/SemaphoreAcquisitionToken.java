package org.fermented.dairy.app.server.concurrency.threads;

import org.fermented.dairy.app.server.concurrency.exceptions.ThreadException;

import java.util.concurrent.Semaphore;

/// A token that represents the acquisition of a permit from a {@link Semaphore}.
///
/// This class implements {@link AutoCloseable} to ensure that the semaphore permit
/// is automatically released when used within a try-with-resources block.
///
/// This class is thread-safe as it relies on the underlying {@link Semaphore}'s 
/// thread-safety properties.
///
/// @since 1.0.0
public class SemaphoreAcquisitionToken implements AutoCloseable {

    private final Semaphore semaphore;

    /// Creates a new token and acquires a permit from the provided {@link Semaphore}.
    ///
    /// @param semaphore The semaphore from which to acquire a permit.
    /// @throws InterruptedException If the thread is interrupted while waiting for a permit.
    /// @since 1.0.0
    SemaphoreAcquisitionToken(final Semaphore semaphore) throws InterruptedException {
        this.semaphore = semaphore;
        semaphore.acquire();
    }

    /// Releases the held permit back to the {@link Semaphore}.
    ///
    /// @since 1.0.0
    @Override
    public void close() {
        semaphore.release();
    }

    /// Acquires a permit from the provided {@link Semaphore} and returns a new token.
    ///
    /// ### Side Effects:
    /// - Acquires a permit from the provided {@link Semaphore}.
    ///
    /// @param semaphore The semaphore from which to acquire a permit.
    /// @return A new instance of {@link SemaphoreAcquisitionToken} representing the held permit.
    /// @throws ThreadException.SemaphoreAcquisitionException If the permit acquisition is interrupted.
    /// @since 1.0.0
    public static SemaphoreAcquisitionToken acquire(final Semaphore semaphore) throws ThreadException {
        try {
            return new SemaphoreAcquisitionToken(semaphore);
        } catch (InterruptedException ie) {
            throw new ThreadException.SemaphoreAcquisitionException("Could not acquire semaphore", ie);
        }

    }
}
