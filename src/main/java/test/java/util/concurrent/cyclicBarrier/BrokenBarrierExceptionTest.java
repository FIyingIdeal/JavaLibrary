package test.java.util.concurrent.cyclicBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author yanchao
 * @date 2018/11/4 13:43
 * @link <a href="https://www.jianshu.com/p/a1d92ceadbe7">Java并发之CyclicBarrier</a>
 * CyclicBarrier 等待线程出现异常的情况
 */
public class BrokenBarrierExceptionTest {

    private static class BrokenTestTask implements Runnable {
        private int id;
        private CyclicBarrier barrier;
        public BrokenTestTask(int id, CyclicBarrier barrier) {
            this.id = id;
            this.barrier = barrier;
        }
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(id);
                System.out.println(Thread.currentThread().getName() + " will await!");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + " " + e.getMessage());
                e.printStackTrace();
                return;
            }
            System.out.println(Thread.currentThread().getName() + " finish await");
        }
    }

    /**
     * 因调用 CyclicBarrier#reset() 方法，导致已经处于等待状态的线程抛出 BrokenBarrierException
     * 而后来进入等待状态的线程可能因为没有足够的线程到达栅栏而一直等待
     */
    private static void reset() throws InterruptedException {
        int parties = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties,
                () -> System.out.println("barrier action"));
        for (int i = 0; i < parties; i++) {
            new Thread(new BrokenTestTask(i, cyclicBarrier)).start();
        }
        TimeUnit.SECONDS.sleep(2);
        cyclicBarrier.reset();
    }

    /**
     * 如果在等待过程中，有线程被中断，也会抛出 BrokenBarrierException。
     * 并且这个异常将会传递给其他所有线程，即使该线程还没有进入等待状态，但一旦其调用 await() 就会抛出该异常
     * @throws InterruptedException
     */
    private static void interrupt() throws InterruptedException {
        int parties = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties,
                () -> System.out.println("barrier action"));
        List<Thread> threads = new ArrayList<>(parties);
        for (int i = 0; i < parties; i++) {
            Thread thread = new Thread(new BrokenTestTask(i, cyclicBarrier));
            threads.add(thread);
            thread.start();
        }
        TimeUnit.SECONDS.sleep(1);
        threads.get(1).interrupt();
    }

    /**
     * 如果在执行屏障操作（CyclicBarrier的第二个参数Runnable）过程中发生异常，则该异常将被传播到当前线程中
     * 其他线程将会抛出 BrokenBarrierException
     * @throws InterruptedException
     */
    private static void barrierActionException() throws InterruptedException {
        int parties = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties,
                () -> {throw new NullPointerException("Barrier Action Exception");});

        for (int i = 0; i < parties; i++) {
            new Thread(new BrokenTestTask(i, cyclicBarrier)).start();
        }
        TimeUnit.SECONDS.sleep(2);
    }

    /**
     * 如果某一个等待栅栏的线程等待超时，那这个线程将会抛出 TimeoutException.
     * 其他线程将会抛出 BrokenBarrierException
     * @throws InterruptedException
     */
    private static void awaitTimeout() throws InterruptedException {
        int parties = 5;
        // 将会添加一个 await() 超时的线程，所以第一个参数 + 1
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties + 1,
                () -> System.out.println("barrier action"));

        Runnable awaitTimeoutTask = () -> {
            System.out.println("awaitTimeoutTask await(2s)");
            try {
                cyclicBarrier.await(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        };
        // await 将超时的线程
        new Thread(awaitTimeoutTask).start();
        for (int i = 0; i < parties; i++) {
            new Thread(new BrokenTestTask(i, cyclicBarrier)).start();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // reset();

        // interrupt();

        // barrierActionException();

        awaitTimeout();

    }
}
