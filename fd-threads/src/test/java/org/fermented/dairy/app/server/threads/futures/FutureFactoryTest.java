package org.fermented.dairy.app.server.threads.futures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FutureFactoryTest {

    final FutureFactory virtual = FutureFactory.virtual(
            2,
            10,
            "testThread_"
    );

    @Test
    void givenVirtualFactory_thenRunTasks_validateDifferentThreadUsed(){
        String currentThreadName = Thread.currentThread().getName();
        String executedThreadName1 = virtual.using(
                () -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return Thread.currentThread().getName();
                }

        ).join();
        String executedThreadName2 = virtual.using(
                () -> {
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return Thread.currentThread().getName();
                }

        ).join();
        System.out.printf("currentThreadName: %s, executedThreadName1: %s, executedThreadName2: %s", currentThreadName, executedThreadName1, executedThreadName2);
        assertNotEquals(currentThreadName, executedThreadName1, "Parent Thread and Virtual thread names must be different");
        assertNotEquals(executedThreadName1, executedThreadName2, "Virtual Thread names must be different");
    }
}