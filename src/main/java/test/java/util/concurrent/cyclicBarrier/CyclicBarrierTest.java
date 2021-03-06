package test.java.util.concurrent.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator on 2017/3/2.
 * CyclicBarrier类是一种同步机制，它能够对处理一些算法的线程实现同步，
 * 即一个所有线程必须等待的栅栏，直到所有的线程都到达这里，然后才能继续执行
 */
public class CyclicBarrierTest {

    static class LatterArrival implements Runnable {
        private CyclicBarrier cyclicBarrier;
        public LatterArrival(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("LatterArriver will sleep...");
            try {
                Thread.sleep(2000);
                System.out.println("LatterArriver arrive cyclicBarrier");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("LatterArriver run...");
        }
    }

    static class Waiter implements Runnable {
        private CyclicBarrier cyclicBarrier;
        public Waiter(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("Waiter will wait other thread arrive...");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("Waiter run...");
        }
    }

    public static void main(String[] args) {
        //构造方法参数是指定当有多少线程在被释放之前等待栅栏
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        //也可以定义多个栅栏让线程“们”等待
        new Thread(new Waiter(cyclicBarrier)).start();
        new Thread(new LatterArrival(cyclicBarrier)).start();

        // 两种让主线程等待的方法，但其实不需要等待，因为这两个线程并非后台线程，主线程执行完毕不会对这两个线程产生任何影响
        /*try {
            // 主线程到达也算一个，这样的话Waiter先等待，然后主线程到达，两者执行
            // 之后LatterArriver到达，但没有达到有两个线程等待的条件，就会一直阻塞
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }*/
        /*try {
            //让主线程睡眠等待，而不是调用cyclicBarrier.await()
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        /*
         * CyclicBarrier支持一个栅栏行动，栅栏行动是一个Runnable实例，一旦最后等待的线程抵达，它就会被执行
         * 而CyclicBarrier是可重用的，所以每一次所有线程都抵达以后，其Runnable都会执行一次
         */
        Runnable barrierAction = ()
                -> System.out.println("barrierAction executed");
        CyclicBarrier barrier = new CyclicBarrier(2, barrierAction);

        new Thread(new Waiter(barrier)).start();
        new Thread(new LatterArrival(barrier)).start();
    }
}
