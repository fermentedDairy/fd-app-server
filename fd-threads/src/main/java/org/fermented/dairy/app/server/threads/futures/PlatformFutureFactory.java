package org.fermented.dairy.app.server.threads.futures;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/// An implementation of {@link FutureFactory} that uses a platform thread pool.
///
/// This factory uses a {@link ThreadPoolExecutor} to manage a pool of platform threads.
///
/// @since 1.0.0
public final class PlatformFutureFactory implements FutureFactory {

    private final Executor platfromExecutor;

    /// Creates a new {@link PlatformFutureFactory} with a specific thread pool configuration.
    ///
    /// @param corePoolSize The number of threads to keep in the pool.
    /// @param maximumPoolSize The maximum number of threads to allow in the pool.
    /// @param keepAliveMillis The time to which threads in the normal pool are allowed to expire after being idle.
    /// @since 1.0.0
    PlatformFutureFactory(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveMillis
    ) {
        this.platfromExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveMillis,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()
        );
    }

    /// Returns the {@link Executor} managed by this factory.
    ///
    /// @return The platform executor.
    /// @since 1.0.0
    @Override
    public Executor executor() {
        return platfromExecutor;
    }
}
