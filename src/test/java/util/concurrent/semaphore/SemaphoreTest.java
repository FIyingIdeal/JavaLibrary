package test.java.util.concurrent.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2017/3/2.
 * Semaphore类是一个计数信号量，它有两个主要的方法：acquire(),release()
 * Semaphore有一个指定数量的“许可”初始化，每调用一次acquire()，一个许可就会被调用线程取走；
 * 每调用一次release()，一个许可就会被还给信号量。在没有任何release()调用的情况下，
 * 最多只能有N（信号量初始化时指定的许可数据量）个线程通过acquire()方法。
 *
 * 公平性：
 * 无法保证第一个调用acquire()的线程会是第一个获得许可的线程，可以在初始化的时候指定fair为true，
 * 即可使Semaphore强制公平。但这样会影响到并发的性能，所以除非确实需要，否则不要启用它。
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5, true);

        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName() + " execute");
                        Thread.sleep(1000);
                        semaphore.release();
                        System.out.println("------------" + semaphore.availablePermits());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            pool.execute(runnable);
        }
        pool.shutdown();
    }
}
