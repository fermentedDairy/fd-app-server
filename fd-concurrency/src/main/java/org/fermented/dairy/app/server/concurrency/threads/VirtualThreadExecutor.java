package org.fermented.dairy.app.server.concurrency.threads;

import java.util.concurrent.Executor;

import static org.fermented.dairy.app.server.concurrency.logging.Logging.THREADS_LOGGER;

/// An interface for an executor that specifically uses virtual threads.
///
/// This interface extends {@link java.util.concurrent.Executor} to explicitly
/// indicate that tasks will be executed within the context of virtual threads.
///
/// @since 1.0.0
public sealed interface VirtualThreadExecutor extends Executor permits BoundedVirtualThreadExecutor {

    /// Creates a bounded virtual thread executor that limits concurrency and wait queue size.
    ///
    /// The executor creates up to [maxConcurrency] virtual threads to execute tasks.
    /// If all slots are occupied and [maxWaitCount] > 0, additional tasks queue up until a slot
    /// becomes available. Once the wait queue is full, new submissions are rejected.
    ///
    /// @param maxConcurrency The maximum number of concurrent virtual threads.
    /// @param maxWaitCount The maximum number of tasks that can wait for a slot; 0 means no queuing (this is "best effort", the number of waiting jobs could exceed this).
    /// @param threadNamePrefix The prefix for virtual thread names.
    /// @return A new bounded virtual thread executor.
    /// @since 1.0.0
    static VirtualThreadExecutor bounded(final int maxConcurrency, final long maxWaitCount, final String threadNamePrefix){
        THREADS_LOGGER.debug("Creating bounded VirtualThreadExecutor with maxConcurrency = {}, maxWaitCount = {}, threadNamePrefix = {}",
                maxConcurrency, maxWaitCount, threadNamePrefix
        );
        return new BoundedVirtualThreadExecutor(
                maxConcurrency,
                maxWaitCount,
                threadNamePrefix
        );
    }
}
