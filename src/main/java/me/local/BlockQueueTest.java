package me.local;

import me.local.utils.ThreadLog;

import java.time.Duration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class BlockQueueTest {

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 1, TimeUnit.HOURS, new LinkedBlockingQueue<>(), new ThreadFactory() {
        private int i = 1;

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("T-" + i);
            i++;
            return thread;
        }
    });

    private void testBlockingQueue() {

        BlockingQueue<String> blockingQueue = new me.local.LinkedBlockingQueue<>(10);
        AtomicLong counter = new AtomicLong(0);
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.submit(new BQOperation(blockingQueue, counter));
            threadPoolExecutor.submit(() -> {
                ThreadLog.log("Start taking");
                ThreadLog.log("Get {}", blockingQueue.poll(Duration.ofMillis(500)));
            });
        }
        try {
            threadPoolExecutor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class BQOperation implements Runnable {
        private BlockingQueue Q;
        private AtomicLong counter;

        public BQOperation(BlockingQueue q, AtomicLong counter) {
            Q = q;
            this.counter = counter;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
                ThreadLog.log("put "+counter.get());
                Q.add("T" + counter.getAndIncrement());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) {
        BlockQueueTest bqt = new BlockQueueTest();
        bqt.testBlockingQueue();
    }

}
