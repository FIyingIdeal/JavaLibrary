package test.java.util.concurrent.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator on 2017/3/2.
 */
public class LatterArriver implements Runnable {

    private CyclicBarrier cyclicBarrier;

    public LatterArriver(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        System.out.println("LatterArriver will sleep...");
        try {
            Thread.sleep(2000);
            System.out.println("LatterArriver arrive cyclicBarrier");
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("LatterArriver run...");

    }
}
