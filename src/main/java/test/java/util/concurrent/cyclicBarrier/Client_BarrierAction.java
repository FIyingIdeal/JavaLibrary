package test.java.util.concurrent.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator on 2017/3/2.
 * CyclicBarrier支持一个栅栏行动，栅栏行动是一个Runnable实例，一旦最后等待的线程抵达，它就会被执行
 * 而CyclicBarrier是可重用的，所以每一次所有线程都抵达以后，其Runnable都会执行一次
 */
public class Client_BarrierAction {
    public static void main(String[] args) {
        Runnable barrierAction = ()
                -> System.out.println("barrierAction executed");
        CyclicBarrier barrier = new CyclicBarrier(2, barrierAction);

        new Thread(new Waiter(barrier)).start();
        new Thread(new LatterArriver(barrier)).start();

    }
}
