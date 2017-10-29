package test.java.util.concurrent.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/3/1.
 */
public class Decrementer implements Runnable {

    private CountDownLatch latch;

    public Decrementer(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        while (latch.getCount() > 0) {
            System.out.println("Decrementer countDown()");
            latch.countDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
