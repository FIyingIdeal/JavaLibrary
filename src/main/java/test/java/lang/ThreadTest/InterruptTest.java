package test.java.lang.ThreadTest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author yanchao
 * @date 2018/5/1 13:04
 * 当在线程上调用interrupt()时，中断发生的唯一时刻是在任务要进入到阻塞操作中或已经在阻塞操作内部时（通过抛出InterruptedException异常来中断线程）。
 *      这意味着如果线程在循环中碰巧没有产生任何阻塞调用的情况下，如果在循环条件中没有使用Thread.interrupted()来判断线程时候被中断，那么即使使用
 *      interrupt()对线程进行了中断操作也是无效的。
 * 使用Thread.interrupted()可以检查中断状态，它不仅可以判断interrupt()方法是否被调用过，还可以清除中断状态。
 *
 * 可以清除中断状态的已知操作：调用Thread.interrupted()； 捕获InterruptedException(Exception)异常
 *
 * @see <a href="https://www.cnblogs.com/baoendemao/p/3804730.html">阻塞（sleep等等）区别 中断（interrupt）+ 中断的意义</a>
 */
public class InterruptTest {

    private static final Logger logger = LoggerFactory.getLogger(InterruptTest.class);

    @Test
    public void interrupt() {
        new Thread() {
            @Override
            public void run() {
                int i = 0;
                try {
                    while (i++ < 30) {
                    // while (i++ < 30 && !Thread.interrupted()) {
                        System.out.println(i + Thread.currentThread().getName() + " is running!");
                        if (i == 2) {
                            Thread.currentThread().interrupt();
                            long x = System.currentTimeMillis();
                            System.out.println("x = " + x);
                            if (x % 2 == 0) {
                                Thread.sleep(1000);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 线程中断相关测试
     */
    @Test
    public void interrupt1() {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                Thread.currentThread().interrupt();
                logger.info ("被中断的线程如果不做相关判断的话，依然可以继续执行 == {}", i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5 && !Thread.interrupted(); i++) {
                Thread.currentThread().interrupt();
                logger.info("被中断的线程如果做了相关判断的话，线程将不会再执行 ==  {}", i);
            }
        });

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过调用 Thread.interrupted() 或 捕获 InterruptedException 异常，都将会清除掉线程的中断状态
     */
    @Test
    public void clearInterruptedFlagByCatchInterruptedException() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5 && !Thread.interrupted(); i++) {
                Thread.currentThread().interrupt();
                try {
                    // 调用 Thread.interrupted() 方法后，线程的中断状态也将会被清除掉，即如果取消下边logger.info的注释，将不会抛出异常
                    // logger.info("Thread.interrupted() = {}", Thread.interrupted());
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    logger.error("中断已睡眠的线程抛出了InterruptedException: i = {}, Thread.interrupted() = {} ", i, Thread.interrupted());
                }
                logger.info("捕获InterruptedException后中断状态将会被清除: i = {}, Thread.interrupted() =  {}", i, Thread.interrupted());
            }
        });
        thread.start();
        // 这里调用join方法 等待thread执行完成，否则可能执行一半
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在已经中断的线程上调用sleep() 将会抛出InterruptedException异常
     */
    @Test
    public void interruptSleep() {
        // 中断当前线程
        Thread.currentThread().interrupt();
        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            logger.error("在一个已被中断的线程中执行阻塞操作（sleep、join、wait等）时将会抛出InterruptedException");
            e.printStackTrace();
        }
    }

    /**
     * 测试中断一个已经sleep的线程：将会抛出InterruptedException异常，通过这种方式可以提前结束线程的睡眠状态，只需要捕获异常不做任何处理
     */
    @Test
    public void sleepInterrupt() {
        Runnable runnable1 = () -> {
            try {
                logger.info("sleepThread 准备 sleep");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                logger.error("sleepThread 抛出异常");
                e.printStackTrace();
            }
            logger.info("线程sleep以后，通过调用其interrupt()方法可以提前结束其sleep");
        };

        class MyRunnable implements Runnable {

            private Thread thread;

            public MyRunnable(Thread thread) {
                this.thread = thread;
            }

            @Override
            public void run() {
                thread.interrupt();
            }
        }

        Thread sleepThread = new Thread(runnable1);
        Thread interruptThread = new Thread(new MyRunnable(sleepThread));
        sleepThread.start();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            logger.error("主线程抛出异常");
            e.printStackTrace();
        }
        interruptThread.start();
    }
}
