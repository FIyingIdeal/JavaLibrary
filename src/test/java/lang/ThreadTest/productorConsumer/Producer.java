package test.java.lang.ThreadTest.productorConsumer;

import java.util.Queue;
import java.util.Random;

/**
 * Created by Administrator on 2016/7/15.
 */
public class Producer extends Thread {

    private Queue<Integer> sharedQueue;
    private int maxSize;

    public Producer(Queue<Integer> sharedQueue, int maxSize, String name) {
        super(name);
        this.sharedQueue = sharedQueue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        System.out.println("Producer start!");
        while (true) {
            synchronized(sharedQueue) {
                while (sharedQueue.size() == maxSize) {
                    System.out.println("sharedQueue is full!");
                    try {
                        sharedQueue.wait();
                    } catch (IllegalMonitorStateException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Random random = new Random();
                int value = random.nextInt();
                System.out.println("Producer product one number : " + value);
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
