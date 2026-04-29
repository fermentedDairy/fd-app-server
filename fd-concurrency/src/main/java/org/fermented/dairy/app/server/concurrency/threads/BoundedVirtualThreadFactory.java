package org.fermented.dairy.app.server.concurrency.threads;

import org.fermented.dairy.app.server.concurrency.exceptions.ThreadException;
import org.fermented.dairy.app.server.concurrency.exceptions.ThreadRuntimeException;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;

import static org.fermented.dairy.app.server.concurrency.logging.LOGGING.THREADS_LOGGER;

/// A {@link ThreadFactory} implementation that creates bounded virtual threads.
///
/// This factory creates virtual threads with semaphore-based concurrency control.
/// It limits the number of concurrent threads executing at any time and provides
/// a bounded wait queue. When the wait queue is full, new thread creation is rejected
/// with a {@link ThreadRuntimeException.TooManyWaitingThreads} exception.
///
/// @since 1.0.0
public class BoundedVirtualThreadFactory implements ThreadFactory {

    private final Semaphore semaphore;
    private final long maxWaitCount;
    private final Thread.Builder threadBuilder;

    /// Creates a new bounded virtual thread factory.
    ///
    /// @param maxConcurrency The maximum number of concurrent virtual threads.
    /// @param maxWaitCount The maximum number of tasks that can wait for a slot (best-effort).
    /// @param threadNamePrefix The prefix for virtual thread names.
    /// @since 1.0.0
    BoundedVirtualThreadFactory(final int maxConcurrency, final long maxWaitCount, final String threadNamePrefix) {
        this.semaphore = new Semaphore(maxConcurrency, true);
        this.maxWaitCount = maxWaitCount;
        this.threadBuilder = Thread.ofVirtual().name(threadNamePrefix, 1);
    }

    /// Creates a new virtual thread that will execute the given command.
    ///
    /// The thread is executed with a semaphore permit that limits concurrency.
    /// If the wait queue has reached [maxWaitCount], a
    /// {@link ThreadRuntimeException.TooManyWaitingThreads} exception is thrown.
    ///
    /// @param command The runnable to execute in the new thread.
    /// @return A new unstarted virtual thread.
    /// @throws ThreadRuntimeException.TooManyWaitingThreads If the wait queue is full.
    /// @since 1.0.0
    @Override
    public Thread newThread(@NotNull Runnable command) {
        Objects.requireNonNull(command);
        if (semaphore.getQueueLength() >= maxWaitCount){
            throw new ThreadRuntimeException.TooManyWaitingThreads(semaphore.getQueueLength());
        }
        return threadBuilder.unstarted(
                () -> {
                    try (var _ = SemaphoreAcquisitionToken.acquire(semaphore)) {
                        command.run();
                    } catch (ThreadException threadException) {
                        handleException(threadException.getMessage(), threadException);
                    } catch (Exception e) {
                        handleException("Exception caught", e);
                    }
                }
        );
    }

    private void handleException(String message, Throwable cause) {
        THREADS_LOGGER.error(message, cause);
        Thread.currentThread().interrupt();
        throw new ThreadRuntimeException.ExecutionException(message, cause);
    }
}
