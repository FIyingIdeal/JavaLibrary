package test.java.util.concurrent.semaphore;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/3/2.
 * Semaphore 类是一个计数信号量，它有两个主要的方法：acquire(), release()
 * Semaphore 有一个指定数量的“许可”初始化，每调用一次 acquire()，一个许可就会被调用线程取走；
 * 每调用一次 release()，一个许可就会被还给信号量。在没有任何 release() 调用的情况下，
 * 最多只能有N（信号量初始化时指定的许可数据量）个线程通过 acquire() 方法。
 *
 * 公平性：
 * 无法保证第一个调用 acquire() 的线程会是第一个获得许可的线程，可以在初始化的时候指定 fair 为 true，即可使 Semaphore 强制公平。
 * 但这样会影响到并发的性能，所以除非确实需要，否则不要启用它。
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

    @Test
    public void acquireAndRelease() {
        Semaphore semaphore = new Semaphore(5, true);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " execute");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 需要将两个 try-catch 分开，因为 Semaphore#acquire() 与 Thread.sleep() 方法抛出的异常一样
                // 当抛出异常的时候并不知道是哪个方法抛出的，如果是 Thread.sleep() 方法抛出的，需要释放许可
                try {
                    TimeUnit.SECONDS.sleep(1000);
                    // 业务逻辑...
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " sleep() 中断！");
                    semaphore.release();
                }

            });
        }
    }

    /**
     * 使用 Semaphore 可以将任何一个容器变为一个有界阻塞容器
     * 如下将一个 Set 变为有界阻塞容器
     */
    class ChangeContainer2LimitedBlockingContainer<T> {
        private final Set<T> set;
        private final Semaphore semaphore;

        public ChangeContainer2LimitedBlockingContainer(int permits) {
            this.set = new HashSet<>();
            this.semaphore = new Semaphore(permits);
        }

        public boolean add(T t) throws InterruptedException {
            semaphore.acquire();
            boolean success = false;
            try {
                success = this.set.add(t);
                return success;
            } finally {
                if (!success) {
                    semaphore.release();
                }
            }
        }

        public boolean remove(T t) {
            boolean removed = this.set.remove(t);
            if (removed) {
                semaphore.release();
            }
            return removed;
        }
    }

    @Test
    public void test1() {
        System.out.println(1.0 / 0.0);
    }
}
