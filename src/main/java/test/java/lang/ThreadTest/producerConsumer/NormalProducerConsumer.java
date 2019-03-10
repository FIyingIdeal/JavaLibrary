package test.java.lang.ThreadTest.producerConsumer;

import utils.PrintUtil;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * @author yanchao
 * @date 2019/3/10 10:13
 * 使用 {@link Object#wait()} 与 {@link Object#notify()} 实现的 生产者-消费者 模型
 */
public class NormalProducerConsumer {

    /**
     * 生产者
     */
    static class Producer extends Thread {

        private Queue<Integer> sharedQueue;
        private int maxSize;

        public Producer(Queue<Integer> sharedQueue, int maxSize, String name) {
            super(name);
            this.sharedQueue = sharedQueue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            PrintUtil.println("Producer start!");
            while (!Thread.interrupted()) {
                synchronized(sharedQueue) {
                    while (sharedQueue.size() == maxSize) {
                        System.out.println("sharedQueue is full!");
                        try {
                            sharedQueue.wait();
                        } catch (IllegalMonitorStateException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    Random random = new Random();
                    int value = random.nextInt();
                    PrintUtil.println("Producer product one number : " + value);
                    sharedQueue.add(value);
                    sharedQueue.notifyAll();
                }
            }
        /*while (true) {
            synchronized(sharedQueue) {
                while (sharedQueue.size() < maxSize) {
                    Random random = new Random();
                    int value = random.nextInt();
                    System.out.println("Producer product one number : " + value);
                    sharedQueue.add(value);
                    sharedQueue.notifyAll();
                }
                System.out.println("sharedQueue is full!");
                try {
                    sharedQueue.wait();
                } catch (IllegalMonitorStateException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }*/
        }
    }

    /**
     * 消费者
     */
    static class Consumer extends Thread {

        private Queue<Integer> sharedQueue;
        private int maxSize;

        public Consumer(Queue<Integer> sharedQueue, int maxSize, String name) {
            super(name);
            this.sharedQueue = sharedQueue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            PrintUtil.println("Consumer start!");
            while (!Thread.interrupted()) {
                synchronized (sharedQueue) {
                    while (sharedQueue.isEmpty()) {
                        System.out.println("Queue is empty");
                        try {
                            sharedQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    Integer value = sharedQueue.remove();
                    PrintUtil.println("Consumer consumed : " + value);
                    sharedQueue.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        PrintUtil.println("How to use wait and notify method in Java");
        PrintUtil.println("Solving Producer Consumper Problem");
        Queue<Integer> sharedQueue = new LinkedList<>();
        int maxSize = 10;
        Thread producer = new Producer(sharedQueue, maxSize, "Producer");
        Thread consumer = new Consumer(sharedQueue, maxSize, "Consumer");
        producer.start();
        consumer.start();
    }
}
