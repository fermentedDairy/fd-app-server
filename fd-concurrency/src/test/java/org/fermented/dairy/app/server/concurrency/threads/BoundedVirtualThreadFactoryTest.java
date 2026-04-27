package org.fermented.dairy.app.server.concurrency.threads;

import org.awaitility.Awaitility;
import org.fermented.dairy.app.server.concurrency.exceptions.ThreadRuntimeException;
import org.fermented.dairy.app.server.concurrency.threads.utils.TestRunner;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class BoundedVirtualThreadFactoryTest {

    @Test
    void givenBoundedVirtualThreadFactory_thenRunTasks_validateDifferentThreadsUsed(){
        var factory = new BoundedVirtualThreadFactory(10, 10, "testThread_");
        var currentThreadName = Thread.currentThread().getName();
        var testRunner1 = new TestRunner(10);
        var testRunner2 = new TestRunner(15);
        factory.newThread(testRunner1).start();
        factory.newThread(testRunner2).start();
        var executedThreadName1 = Awaitility.await().atLeast(Duration.ofMillis(10)).until(
                () -> testRunner1.threadName,
                Objects::nonNull
        );
        var executedThreadName2 = Awaitility.await().atLeast(Duration.ofMillis(10)).until(
                () -> testRunner2.threadName,
                Objects::nonNull
        );


        System.out.printf("currentThreadName: %s, executedThreadName1: %s, executedThreadName2: %s", currentThreadName, executedThreadName1, executedThreadName2);
        assertNotEquals(currentThreadName, executedThreadName1, "Parent Thread and Virtual thread names must be different");
        assertNotEquals(executedThreadName1, executedThreadName2, "Virtual Thread names must be different");
    }

    @Test
    void givenBoundedVirtualThreadFactory_thenRunTasksExceedingAllowedQueueDepth_validateExceptionThrown(){
        var factory = new BoundedVirtualThreadFactory(1, 2, "testThread_");
        var enqueuedJobCount = new AtomicInteger(0);
        assertThrows(ThreadRuntimeException.TooManyWaitingThreads.class,
                () -> {
                    //noinspection InfiniteLoopStatement, thats the point
                    while(true) {
                        factory.newThread(new TestRunner(10)).start();
                        enqueuedJobCount.incrementAndGet();
                    }
                });
    }
}