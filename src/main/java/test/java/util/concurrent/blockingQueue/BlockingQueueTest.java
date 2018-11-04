package test.java.util.concurrent.blockingQueue;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/2/27.
 * 描述 ：BlockingQueue接口表示一个线程安放入和提取实例的队列
 * 一般用法：通常用于一个线程生产对象，另一个线程消费这些对象的场景
 */
public class BlockingQueueTest {

    /**
     * ArrayBlockingQueue 数组阻塞队列
     * 是一个有界的阻塞队列，其内部实现是将对象放到一个数组里
     * 初始化设定存储元素数量的上限后就无法进行修改了（符合数组的特性）
     */
    @Test
    public void ArrayBlockingQueueTest() {
        //初始化设定存储元素数量的上限后就无法进行修改了
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void offer() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(2);
        queue.offer(1);
        queue.offer(2);
        if (queue.offer(3, 2, TimeUnit.SECONDS)) {
            System.out.println("元素插入成功");
        } else {
            System.out.println("元素插入超时");
        }
    }

    @Test
    public void DelayQueueTest() {
        DelayQueue<DelayQueueElement> queue = new DelayQueue<>();
        queue.put(new DelayQueueElement());
        try {
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
