package org.fermented.dairy.app.server.concurrency.structured;

import org.fermented.dairy.app.server.concurrency.threads.BoundedVirtualThreadFactory;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.function.Supplier;

@SuppressWarnings("preview")
public class BoundedStructuredTaskScopeFactory<T> implements Supplier<StructuredTaskScope<T, Void>> {

    private final BoundedVirtualThreadFactory threadFactory;

    public BoundedStructuredTaskScopeFactory(int maxConcurrency, long maxWaitTimeout, String threadNamePrefix) {
        this.threadFactory = new BoundedVirtualThreadFactory(
                maxConcurrency,
                maxWaitTimeout,
                threadNamePrefix
        );
    }

    @Override
    public StructuredTaskScope<T, Void> get() {
        final Joiner<T, Void> joiner = StructuredTaskScope.Joiner.awaitAllSuccessfulOrThrow();
        return StructuredTaskScope.open(joiner, cf -> cf.withThreadFactory(threadFactory));
    }
}
