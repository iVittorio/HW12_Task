package ru.sbt.task.task;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by i.viktor on 04/09/16.
 */
public class Task<T> {
    private final Callable<T> callable;
    private volatile boolean isCalculated = false;
    private volatile T result;
    private volatile boolean isException = false;
    private final Lock lock = new ReentrantLock();
    private Exception e;

    public Task(Callable<T> callable) {
        this.callable = callable;
    }

    public T get() {
        if (isException) throw new RuntimeException(e);

        if (!isCalculated) {
            lock.lock();
            if (isException) throw new RuntimeException(e);
            if (!isCalculated) {
                try {
                    result = callable.call();
                    isCalculated = true;
                } catch (Exception e) {
                    isException = true;
                    isCalculated = true;
                    this.e = e;
                    throw new RuntimeException(e);
                }
            }
            lock.unlock();
        }
        return result;
    }
}
