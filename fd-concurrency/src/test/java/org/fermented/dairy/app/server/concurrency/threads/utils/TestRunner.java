package org.fermented.dairy.app.server.concurrency.threads.utils;

public final class TestRunner implements Runnable {

    public String threadName = null;
    final int waitTime;

    public TestRunner(int waitTime) {
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
