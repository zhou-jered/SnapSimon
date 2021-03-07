package me.local;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingQueue<T> implements BlockingQueue<T> {

    public LinkedBlockingQueue() {
        this(Integer.MAX_VALUE);
    }

    public LinkedBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void add(T o) throws InterruptedException {
        try {
            putLock.lockInterruptibly();
            if (capacity == size.get()) {
                notFullCondition.await();
            }
            Node<T> newTail = new Node<>(o);
            while (true) {
                Node<T> oldTail = tail.get();
                if (oldTail != null && oldTail.next == null) {
                    oldTail.next = newTail;
                }
                if (tail.compareAndSet(oldTail, newTail)) {
                    size.incrementAndGet();
                    notEmptyCondition.signal();
                    break;
                }
            }

        } finally {
            putLock.unlock();
        }
    }

    @Override
    public boolean offer(T o) throws InterruptedException {
        try {
            putLock.lockInterruptibly();
            if (capacity == size.get()) {
                return false;
            }
            Node<T> newTail = new Node<>(o);
            Node<T> oldTail = tail.get();
            if (tail.compareAndSet(oldTail, newTail)) {
                size.incrementAndGet();
                notEmptyCondition.signal();
                return true;
            }
            return false;
        } finally {
            putLock.unlock();
        }
    }

    @Override
    public boolean offer(T o, Duration timeout) throws InterruptedException {
        try {
            putLock.lockInterruptibly();
            if (capacity == size.get()) {
                notFullCondition.await(timeout.get(ChronoUnit.MILLIS), TimeUnit.MILLISECONDS);
            }
            Node<T> newTail = new Node<>(o);
            Node<T> oldTail = tail.get();
            if (tail.compareAndSet(oldTail, newTail)) {
                size.incrementAndGet();
                notEmptyCondition.signal();
                return true;
            }
            return false;
        } finally {
            putLock.unlock();
        }
    }

    @Override
    public T take() {
        Node<T> topNode = head.get();
        while (topNode == null) {
            try {
                takeLock.lockInterruptibly();
                topNode = head.get();
                if (topNode == null) {
                    notEmptyCondition.await();
                }
                topNode = head.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                takeLock.unlock();
            }
        }
        while (true) {
            Node<T> oldHead = head.get();
            Node next = head.get().next;
            if (head.compareAndSet(oldHead, next)) return oldHead.ele;
        }
    }

    @Override
    public T poll(Duration timeout) {
        Node<T> topNode = head.get();
        if (topNode == null) {
            try {
                putLock.lockInterruptibly();
                topNode = head.get();
                if (topNode == null) {
                    notEmptyCondition.await(timeout.get(ChronoUnit.MILLIS), TimeUnit.HOURS);
                }
                topNode = head.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                putLock.unlock();
            }
        }
        if (topNode != null) {
            while (true) {
                Node<T> oldHead = head.get();
                Node next = head.get().next;
                if (head.compareAndSet(oldHead, next)) return oldHead.ele;
            }

        }
        return null;
    }

    @Override
    public T peek(Duration timeout) {
        Node<T> peekNode = head.get();
        if (peekNode == null) {
            try {
                putLock.lockInterruptibly();
                peekNode = head.get();
                if (peekNode == null) {
                    notEmptyCondition.wait(timeout.get(ChronoUnit.MILLIS));
                }
                peekNode = head.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                putLock.unlock();
            }
        }

        if (peekNode != null) {
            return peekNode.ele;
        }
        return null;
    }

    @Override
    public int size() {
        return size.get();
    }

    @Override
    public void clear() {
        Node newHead = new Node(null);
        while (true) {
            Node oldHead = head.get();
            if (head.compareAndSet(oldHead, newHead)) {
                break;
            }
        }
    }


    private int capacity = 0;
    private AtomicInteger size = new AtomicInteger(0);
    private AtomicReference<Node> head = new AtomicReference<>();
    private AtomicReference<Node> tail = new AtomicReference<>();
    private Lock putLock = new ReentrantLock();
    private Condition notFullCondition = putLock.newCondition();
    private Lock takeLock = new ReentrantLock();
    private Condition notEmptyCondition = takeLock.newCondition();


    static class Node<T> {
        T ele;
        Node next;

        public Node(T ele) {
            this.ele = ele;
            next = null;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return next;
        }
    }
}
