package test.java.lang.ThreadTest.productorConsumer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Administrator on 2016/7/15.
 */
public class WaitAndNotifyTest {

    public static void main(String[] args) {
        System.out.println("How to use wait and notify method in Java");
        System.out.println("Solving Producer Consumper Problem");
        Queue<Integer> sharedQueue = new LinkedList<>();
        int maxSize = 10;
        Thread producer = new Producer(sharedQueue, maxSize, "Producer");
        Thread consumer = new Consumer(sharedQueue, maxSize, "Consumer");
        producer.start();
        consumer.start();
    }
}
