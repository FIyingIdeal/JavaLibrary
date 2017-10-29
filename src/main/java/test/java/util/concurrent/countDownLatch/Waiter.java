package test.java.util.concurrent.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/3/1.
 */
public class Waiter implements Runnable {

    private CountDownLatch latch;

    public Waiter(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Waiter Released!");
    }
}
