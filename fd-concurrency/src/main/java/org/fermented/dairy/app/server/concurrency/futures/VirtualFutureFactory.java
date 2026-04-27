package org.fermented.dairy.app.server.concurrency.futures;

import org.fermented.dairy.app.server.concurrency.threads.BoundedVirtualThreadExecutor;
import org.fermented.dairy.app.server.concurrency.threads.VirtualThreadExecutor;

import java.util.concurrent.Executor;

/// An implementation of {@link FutureFactory} that uses virtual threads with concurrency control.
///
/// This factory uses {@link BoundedVirtualThreadExecutor} to manage virtual threads.
///
/// @since 1.0.0
public final class VirtualFutureFactory implements FutureFactory {

    private final VirtualThreadExecutor virtualExecutor;

    /// Creates a new {@link VirtualFutureFactory} with concurrency control.
    ///
    /// @param maxConcurrency The maximum number of concurrently executing tasks.
    /// @param maxWaitCount The maximum number of tasks allowed to wait in the queue.
    /// @param threadNamePrefix The prefix for the name of the virtual threads.
    /// @since 1.0.0
    VirtualFutureFactory(final int maxConcurrency, final long maxWaitCount, final String threadNamePrefix) {
        this.virtualExecutor = VirtualThreadExecutor.bounded(maxConcurrency, maxWaitCount, threadNamePrefix);
    }

    /// Returns the {@link Executor} managed by this factory.
    ///
    /// @return The virtual executor.
    /// @since 1.0.0
    @Override
    public Executor executor() {
        return virtualExecutor;
    }
}
