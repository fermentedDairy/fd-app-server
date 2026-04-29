package org.fermented.dairy.app.server.concurrency.threads;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/// An implementation of {@link VirtualThreadExecutor} that uses bounded virtual threads.
///
/// This executor creates virtual threads via {@link BoundedVirtualThreadFactory} to execute tasks.
/// It limits the number of concurrent threads using a semaphore-based approach and
/// rejects tasks when the wait queue is full.
///
/// @since 1.0.0
public final class BoundedVirtualThreadExecutor implements VirtualThreadExecutor {

    private final BoundedVirtualThreadFactory threadFactory;
    /// Creates a new bounded virtual thread executor.
    ///
    /// @param maxConcurrency The maximum number of concurrent virtual threads.
    /// @param maxWaitCount The maximum number of tasks that can wait for a slot (best-effort).
    /// @param threadNamePrefix The prefix for virtual thread names.
    /// @since 1.0.0
    BoundedVirtualThreadExecutor(final int maxConcurrency, final long maxWaitCount, final String threadNamePrefix) {
        threadFactory = new BoundedVirtualThreadFactory(maxConcurrency, maxWaitCount, threadNamePrefix);
    }

    /// Executes the specified command as a task.
    ///
    /// This task is executed in a new virtual thread, subject to the concurrency limits
    /// of the executor.
    ///
    /// @param command The task to execute.
    /// @throws NullPointerException if the command is null.
    /// @since 1.0.0
    @Override
    public void execute(@NotNull final Runnable command) {
        Objects.requireNonNull(command);
        threadFactory.newThread(command).start();
    }

}
