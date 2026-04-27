package org.fermented.dairy.app.server.concurrency.futures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static org.fermented.dairy.app.server.concurrency.logging.LOGGING.THREADS_FUTURES_LOGGER;

/// A factory for creating {@link FutureFactory} implementations that use different threading models.
///
/// This interface provides a way to create factories for both platform threads and virtual threads.
///
/// @since 1.0.0
public sealed interface FutureFactory permits PlatformFutureFactory, VirtualFutureFactory {

    /// Creates a {@link FutureFactory} that uses virtual threads with concurrency control.
    ///
    /// @param maxConcurrency The maximum number of concurrently executing tasks.
    /// @param maxWaitCount The maximum number of tasks allowed to wait in the queue.
    /// @param threadNamePrefix The prefix for the name of the virtual threads.
    /// @return A {@link FutureFactory} implementation using virtual threads.
    /// @since 1.0.0
    static FutureFactory virtual(final int maxConcurrency, final long maxWaitCount, final String threadNamePrefix) {
        THREADS_FUTURES_LOGGER.debug(
                "creating virtual future factory with maxConcurrency = {}, maxWaitCount = {}, threadNamePrefix = {}", maxConcurrency, maxWaitCount, threadNamePrefix
        );
        return new VirtualFutureFactory(maxConcurrency, maxWaitCount, threadNamePrefix);
    }

    /// Creates a {@link FutureFactory} that uses platform threads.
    ///
    /// @param corePoolSize The number of threads to keep in the pool.
    /// @param maximumPoolSize The maximum number of threads to allow in the pool.
    /// @param keepAliveMillis The time to which threads in the normal pool are allowed to expire after being idle.
    /// @return A {@link FutureFactory} implementation using platform threads.
    /// @since 1.0.0
    static FutureFactory platform(int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveMillis) {
        THREADS_FUTURES_LOGGER.debug(
                "creating platform future factory with corePoolSize = {}, maximumPoolSize = {}, keepAliveMillis = {}", corePoolSize, maximumPoolSize, keepAliveMillis
        );
        return new PlatformFutureFactory(corePoolSize, maximumPoolSize, keepAliveMillis);
    }

    /// Executes a task using the executor provided by this factory and returns a {@link CompletableFuture}.
    ///
    /// @param <T> The type of the result.
    /// @param supplier The task to be executed.
    /// @return A {@link CompletableFuture} representing the result of the task.
    /// @since 1.0.0
    default <T> CompletableFuture<T> using(Supplier<T> supplier) {
        THREADS_FUTURES_LOGGER.debug("Executing task and creating future");
        return (new CompletableFuture<T>()).completeAsync(supplier, executor());
    }

    /// Returns the {@link Executor} managed by this factory.
    ///
    /// @return The executor to use for tasks.
    /// @since 1.0.0
    Executor executor();
}
