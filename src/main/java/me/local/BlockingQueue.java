package me.local;

import java.time.Duration;

public interface BlockingQueue<T> {
    /**
     * put the element, blocking until available
     *
     * @param o
     */
    void add(T o) throws InterruptedException;


    boolean offer(T o) throws InterruptedException;

    boolean offer(T o, Duration timeout) throws InterruptedException;

    /**
     * get the element until available
     *
     * @return
     */
    T take() throws InterruptedException;

    /**
     * get the element and remove it
     *
     * @param timeout
     * @return
     */
    T poll(Duration timeout);

    /**
     * get the element but not remove it
     *
     * @param timeout
     * @return
     */
    T peek(Duration timeout);

    int size();

    void clear();

}
