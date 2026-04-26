package org.fermented.dairy.app.server.threads;

import org.fermented.dairy.app.server.threads.exceptions.ThreadException;
import org.fermented.dairy.app.server.threads.exceptions.ThreadRuntimeException;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.Semaphore;

import static org.fermented.dairy.app.server.threads.logging.LOGGING.THREADS_LOGGER;

/// An implementation of {@link VirtualThreadExecutor} that uses virtual threads to execute tasks,
/// with concurrency controlled by a {@link Semaphore}.
///
/// While the number of virtual threads created can grow, the number of concurrently executing
/// tasks is limited by the provided {@code maxConcurrency} value.
///
/// This class is thread-safe.
///
/// @since 1.0.0
public final class BoundedVirtualThreadExecutor implements VirtualThreadExecutor {

    private final Semaphore semaphore;
    private final long maxWaitCount;
    private final Thread.Builder threadBuilder;

    BoundedVirtualThreadExecutor(final int maxConcurrency, final long maxWaitCount, final String threadNamePrefix) {
        this.semaphore = new Semaphore(maxConcurrency, true);
        this.maxWaitCount = maxWaitCount;
        this.threadBuilder = Thread.ofVirtual()
                .name(threadNamePrefix, 1);
    }

    /// Executes the given command in a new virtual thread.
    ///
    /// The execution of the command is subject to the concurrency limit established
    /// during the construction of this executor.
    ///
    /// ### Side Effects:
    /// - Spawns a new virtual thread.
    /// - Acquires a permit from the internal semaphore, which may delay the execution of the command if the limit is reached.
    ///
    /// @param command The task to be executed.
    /// @since 1.0.0
    @Override
    public void execute(@NotNull final Runnable command) {
        Objects.requireNonNull(command);
        if (semaphore.getQueueLength() >= maxWaitCount){
            throw new ThreadRuntimeException.TooManyWaitingThreads(semaphore.getQueueLength());
        }
        threadBuilder.start(() -> {
            try (var _ = SemaphoreAcquisitionToken.acquire(semaphore)) {
                command.run();
            } catch (ThreadException threadException) {
                handleException(threadException.getMessage(), threadException);
            } catch (Exception e) {
                handleException("Exception caught", e);
            }
        });
    }

    private void handleException(String message, Throwable cause) {
        THREADS_LOGGER.error(message, cause);
        Thread.currentThread().interrupt();
        throw new ThreadRuntimeException.ExecutionException(message, cause);
    }

}
