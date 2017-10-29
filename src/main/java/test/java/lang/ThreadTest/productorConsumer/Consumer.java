package test.java.lang.ThreadTest.productorConsumer;

import java.util.Queue;

/**
 * Created by Administrator on 2016/7/15.
 */
public class Consumer extends Thread {

    private Queue<Integer> sharedQueue;
    private int maxSize;

    public Consumer(Queue<Integer> sharedQueue, int maxSize, String name) {
        super(name);
        this.sharedQueue = sharedQueue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        System.out.println("Consumer start!");
        while (true) {
            synchronized (sharedQueue) {
                while (sharedQueue.isEmpty()) {
                    System.out.println("Queue is empty");
                    try {
                        sharedQueue.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Integer value = sharedQueue.remove();
                System.out.println("Consumer consumed : " + value);
                sharedQueue.notifyAll();
            }
        }
    }
}
