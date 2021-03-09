package me.local;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueue<T> implements BlockingQueue<T> {

    private Object[] elements;
    private AtomicInteger head = new AtomicInteger(-1); // the position of read
    private AtomicInteger tail = new AtomicInteger(0);  // the position of write
    private AtomicInteger size = new AtomicInteger(0);
    private int capacity = 0;
    private ReentrantLock putLock = new ReentrantLock();
    private ReentrantLock takeLock = new ReentrantLock();

    public ArrayBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[capacity];
    }

    @Override
    public void add(T o) throws InterruptedException {

        int idx = tail.getAndIncrement() % capacity;
        elements[idx] = o;
    }

    @Override
    public boolean offer(T o) {
        return offer(o, Duration.ofMillis(0));
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

    /**
     * get but not remove it
     *
     * @param timeout
     * @return
     */
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
        head.set(-1);
        tail.set(0);
        size.set(0);
    }

}
