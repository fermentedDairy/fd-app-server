package org.fermented.dairy.app.server.threads;


import org.awaitility.Awaitility;
import org.fermented.dairy.app.server.threads.exceptions.ThreadRuntimeException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class VirtualThreadExecutorTest {

    @Test
    void givenBoundedVirtualThreadExecutor_thenRunTasks_validateDifferentThreadsUsed(){
        var executor = VirtualThreadExecutor.bounded(10, 10, "testThread_");
        var currentThreadName = Thread.currentThread().getName();
        var testRunner1 = new TestRunner(10);
        var testRunner2 = new TestRunner(15);
        executor.execute(testRunner1);
        executor.execute(testRunner2);
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
    void givenBoundedVirtualThreadExecutor_thenRunTasksExceedingAllowedQueueDepth_validateExceptionThrown(){
        var executor = VirtualThreadExecutor.bounded(1, 2, "testThread_");
        var enqueuedJobCount = new AtomicInteger(0);
        assertThrows(ThreadRuntimeException.TooManyWaitingThreads.class,
                () -> {
                    //noinspection InfiniteLoopStatement, thats the point
                    while(true) {
                        executor.execute(new TestRunner(10));
                        enqueuedJobCount.incrementAndGet();
                    }
                });
    }

    private static final class TestRunner implements Runnable {

        String threadName = null;
        final int waitTime;

        private TestRunner(int waitTime) {
            this.waitTime = waitTime;
        }

        @Override
        public void run() {
            threadName = Thread.currentThread().getName();
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}