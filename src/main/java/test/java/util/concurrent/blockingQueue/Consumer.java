package test.java.util.concurrent.blockingQueue;

import java.util.concurrent.BlockingQueue;

/**
 * @author  yanchao
 * @date    2017/2/27
 */
public class Consumer implements Runnable {

    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
