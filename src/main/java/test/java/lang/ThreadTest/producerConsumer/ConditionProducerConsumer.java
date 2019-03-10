package test.java.lang.ThreadTest.producerConsumer;

import utils.PrintUtil;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yanchao
 * @date 2019/3/10 10:26
 * 使用 {@link ReentrantLock#newCondition()} 实现 生产者-消费者 模型
 *
 * TODO 考虑当生产者或消费者中的其一被中断，如何将另一个也中断，不然的话就会导致一个中断，另一个一直被阻塞的情况
 */
public class ConditionProducerConsumer {

    private static final int MAX_SIZE = 10;
    private static final Random random = new Random(47);

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition notEmpty = lock.newCondition();
        Condition notFull = lock.newCondition();
        Queue<Integer> queue = new LinkedBlockingQueue<>(MAX_SIZE);
        Thread producer = new Thread(new Producer(queue, lock, notEmpty, notFull));
        Thread consumer = new Thread(new Consumer(queue, lock, notEmpty, notFull));
        producer.start();
        consumer.start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            PrintUtil.println("main thread sleep interrupt!");
            Thread.currentThread().interrupt();
        }
        producer.interrupt();
        consumer.interrupt();
    }


    static class Producer implements Runnable {

        private Queue<Integer> queue;
        private Lock lock;
        private Condition notEmpty;
        private Condition notFull;

        private Producer(Queue<Integer> queue, ReentrantLock lock,
                         Condition notEmpty, Condition notFull) {
            this.queue = queue;
            this.lock = lock;
            this.notEmpty = notEmpty;
            this.notFull = notFull;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                this.lock.lock();
                try {
                    while (this.queue.size() == MAX_SIZE && !Thread.interrupted()) {
                        try {
                            PrintUtil.println("Producer wait queue not full!");
                            this.notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    int num = random.nextInt(20);
                    PrintUtil.println(">>>>> product : " + num);
                    this.queue.offer(num);
                    this.notEmpty.signal();
                } finally {
                    this.lock.unlock();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        private Queue<Integer> queue;
        private Lock lock;
        private Condition notEmpty;
        private Condition notFull;

        private Consumer(Queue<Integer> queue, ReentrantLock lock,
                         Condition notEmpty, Condition notFull) {
            this.queue = queue;
            this.lock = lock;
            this.notEmpty = notEmpty;
            this.notFull = notFull;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                this.lock.lock();
                try {
                    while (this.queue.isEmpty() && !Thread.interrupted()) {
                        try {
                            PrintUtil.println("Consumer wait queue not empty!");
                            this.notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    int num = this.queue.poll();
                    PrintUtil.println("<<<<<< consume : " + num);
                    this.notFull.signal();
                } finally {
                    this.lock.unlock();
                }
            }
        }
    }
}
