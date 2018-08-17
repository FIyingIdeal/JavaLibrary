package test.java.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author yanchao
 * @date 2018/8/6 18:28
 * 自实现自旋锁
 * 自旋锁是在获取不到锁时进行自旋，自旋在了获取锁的操作。
 * 来自百度百科的解释：如果在获取自旋锁时锁已经有保持者，那么获取锁操作将自旋在那里，直到该自旋锁的保持者释放了锁
 *
 * @see <a href="http://ifeve.com/java_lock_see1/">自旋锁</a>
 */
public class SpinLock {

    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        // 如果 atomicReference 不为 null，compareAndSet() 将会返回false，代表这个锁被其他线程占用，则执行“自旋”
        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println(thread.getName() + " is waiting SpinLock");
        }
    }

    public void unlock() {
        // 释放自旋锁，当占用自旋锁的线程为当前线程且准备释放锁时，将 atomicReference 置为 null，以便 lock() 停止自旋
        atomicReference.compareAndSet(Thread.currentThread(), null);
    }

    static class Task implements Runnable {

        private SpinLock spinLock;

        public Task(SpinLock spinLock) {
            this.spinLock = spinLock;
        }

        @Override
        public void run() {
            spinLock.lock();
            System.out.println(Thread.currentThread().getName() + " is executing!");
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                spinLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        SpinLock spinLock = new SpinLock();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Task(spinLock));
        executorService.execute(new Task(spinLock));
        TimeUnit.SECONDS.sleep(2);
        executorService.shutdown();
    }
}
