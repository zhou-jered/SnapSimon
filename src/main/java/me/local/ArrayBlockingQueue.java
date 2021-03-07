package me.local;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueue<T> implements BlockingQueue<T> {

    private T[] elements;
    private AtomicInteger head = new AtomicInteger(-1); // the position of read
    private AtomicInteger tail = new AtomicInteger(0);  // the position of write
    private int capacity = 0;
    private ReentrantLock putLock = new ReentrantLock();
    private ReentrantLock takeLock = new ReentrantLock();

    @Override
    public void add(T o) throws InterruptedException {
        putLock.lockInterruptibly();
        if (tail.get() > head.get()) {

        }
    }

    @Override
    public boolean offer(T o) {
        return false;
    }

    @Override
    public boolean offer(T o, Duration timeout) {
        return false;
    }

    @Override
    public T take() {
        return null;
    }

    @Override
    public T poll(Duration timeout) {
        return null;
    }

    @Override
    public T peek(Duration timeout) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    private void ensureCapacity(int capacity) {
        this.capacity = capacity;
    }
}
