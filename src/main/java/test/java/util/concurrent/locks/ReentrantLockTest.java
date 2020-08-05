package test.java.util.concurrent.locks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.PrintUtil;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yanchao
 * @date 2019/3/9 12:25
 */
public class ReentrantLockTest {

    private ExecutorService pool;

    public static void main(String[] args) {
        ReentrantLockTest test = new ReentrantLockTest();
        test.init();
        test.getWaitingThreads();
        test.close();
    }

    @Before
    public void init() {
        pool = Executors.newFixedThreadPool(5);
    }

    @After
    public void close() {
        if (pool != null && !pool.isShutdown()) {
            pool.shutdown();
        }
    }

    private static class LockTestTask implements Runnable {

        private ReentrantLock lock;
        private String name;

        LockTestTask(ReentrantLock lock) {
            this.lock = lock;
            this.name = "Undified";
        }

        LockTestTask(ReentrantLock lock, String name) {
            this.lock = lock;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " will get lock thread : " + Thread.currentThread());
                lock.lock();
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("Release lock thread : " + Thread.currentThread());
            }
        }
    }

    /**
     * 普通加锁 {@link java.util.concurrent.locks.ReentrantLock}
     */
    @Test
    public void lock() throws Exception {
        ReentrantLock lock = new ReentrantLock();

        Runnable runnable = new LockTestTask(lock);

        pool.submit(runnable);
        pool.submit(runnable);
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * 公平锁。默认情况下， ReentrantLock 是非公平的。
     * 在构造 {@link ReentrantLock} 的时候传递 fair 参数 true，使其成为公平锁
     */
    @Test
    public void fairLock() throws Exception {
        // 构造方法指定该锁为公平锁
        ReentrantLock lock = new ReentrantLock(true);
        for (int i = 0; i < 5; i++) {
            // 依次申请锁，也会依次得到锁
            Runnable runnable = new LockTestTask(lock, String.valueOf(i));
            pool.submit(runnable);
        }

        pool.awaitTermination(6, TimeUnit.SECONDS);
    }

    private static class InterruptLockTask implements Runnable {
        private Lock lock1;
        private Lock lock2;

        InterruptLockTask(Lock lock1, Lock lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            try {
                // 如果锁被占用则会阻塞，阻塞过程中线程中断的话就会抛出 InterruptedException 异常，
                lock1.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + " get lock");
                TimeUnit.SECONDS.sleep(2);
                lock2.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + " get another lock");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock1.unlock();
            }
        }
    }

    /**
     * {@link ReentrantLock#lockInterruptibly()} 方法可响应中断。如果要获取的锁被其他线程持有，
     * 则该方法会阻塞，但阻塞过程中线程被中断，则将会抛出 Interrupted 异常。
     */
    @Test
    public void lockInterruptibly() throws Exception {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();
        // 手动造成死锁
        Runnable runnable1 = new InterruptLockTask(lock1, lock2);
        Runnable runnable2 = new InterruptLockTask(lock2, lock1);
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        TimeUnit.SECONDS.sleep(3);
        // 中断 thread1，lockInterruptibly 将会监听到中断，然后线程抛出异常退出
        thread1.interrupt();
    }

    /**
     * {@link ReentrantLock#tryLock()} 用于尝试获取锁，如果获取到锁则返回true。若没有获取到锁，则立即返回false，不等待；
     * {@link ReentrantLock#tryLock(long, TimeUnit)} 与 tryLock() 方法相似，区别在于如果未获取到锁，会等待指定的时间，
     *      在这个时间范围内获取到则返回 true，否则返回false。
     */
    @Test
    public void tryLock() throws Exception {
        ReentrantLock lock = new ReentrantLock();

        Runnable runnable = () -> {
            try {
                if (lock.tryLock(1100, TimeUnit.MILLISECONDS)) {
                    System.out.println(Thread.currentThread().getName() + " get lock! Do something~");
                    TimeUnit.SECONDS.sleep(1);
                } else {
                    System.out.println(Thread.currentThread().getName() + " don't get lock!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 只有当前线程持有锁的所有才能释放锁，否则会抛出 {@link IllegalMonitorStateException}
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + " release lock");
                }
            }
        };

        for (int i = 0; i < 5; i++) {
            pool.submit(runnable);
        }
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * {@link ReentrantLock#getHoldCount()} 返回当前持有 ReentrantLock 锁的线程
     * 执行 lock() 相关方法（包括tryLock() 和 lockInterruptibly()）的次数。即统计重入次数
     */
    @Test
    public void getHoldCount() {
        ReentrantLock lock = new ReentrantLock();
        holdCountM1(lock);
    }

    private void holdCountM1(ReentrantLock lock) {
        try {
            lock.lock();
            System.out.println("M1 getHoldCount result is : " + lock.getHoldCount());
            holdCountM2(lock);
        } finally {
            lock.unlock();
        }
    }

    private void holdCountM2(ReentrantLock lock) {
        try {
            lock.lockInterruptibly();
            System.out.println("M2 getHoldCount result is : " + lock.getHoldCount());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@link ReentrantLock#getQueueLength()} 方法用于获取等待此锁的线程数
     */
    @Test
    public void getQueueLength() throws Exception {
        ReentrantLock lock = new ReentrantLock();
        Runnable runnable = () -> {
            try {
                lock.lock();
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + " get lock! " +
                        "Queue length is " + lock.getQueueLength());
            } catch(InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };

        // 设定线程数 10 要大于 线程池数量 5. 执行的时候线程池中的只有一个线程在执行，所以开始打印处理的是 4.
        // 开始多余的任务都是在线程池中排队，而不再等待锁的队列中
        for (int i = 0; i < 10; i++) {
            pool.submit(runnable);
        }

        pool.awaitTermination(10, TimeUnit.SECONDS);
    }

    /**
     * {@link ReentrantLock#hasQueuedThreads()} 用于判断是否有线程在等待当前锁
     * {@link ReentrantLock#hasQueuedThread(Thread)} 用于判断给定线程是否在等待当前锁
     */
    @Test
    public void hasQueueThread() throws Exception {
        ReentrantLock lock = new ReentrantLock();

        Runnable runnable = () -> {
            try {
                lock.lock();
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();

        // 给线程时间进行启动，但不要超过线程的睡眠时间
        TimeUnit.SECONDS.sleep(1);
        // true 有线程在等待锁
        System.out.println(lock.hasQueuedThreads());
        // false thread1 没有在等待锁
        System.out.println(lock.hasQueuedThread(thread1));
        // true  thread2 在等待锁
        System.out.println(lock.hasQueuedThread(thread2));
    }

    /**
     * {@link ReentrantLock#isFair()} 用于判断是否是公平锁
     */
    @Test
    public void isFair() {
        ReentrantLock lock = new ReentrantLock();
        System.out.println(lock.isFair());

        lock = new ReentrantLock(true);
        System.out.println(lock.isFair());
    }

    /**
     * {@link ReentrantLock#isHeldByCurrentThread()} 用于判断当前线程是否持有该锁
     */
    @Test
    public void isHeldByCurrentThread() throws Exception {
        ReentrantLock lock = new ReentrantLock();

        Runnable runnable = () -> {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " is held by current thread ? " + lock.isHeldByCurrentThread());
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();

        // 给线程时间进行启动，但不要超过线程的睡眠时间
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println(Thread.currentThread().getName() + " is held by current thread ?? " + lock.isHeldByCurrentThread());

        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * {@link ReentrantLock#isLocked()} 用于判断当前锁是否被任意线程持有。若已被任意线程持有，则返回true。否则返回false
     */
    @Test
    public void isLocked() throws Exception {
        ReentrantLock lock = new ReentrantLock();

        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " isLocked ? " + lock.isLocked());
            try {
                TimeUnit.SECONDS.sleep(1);
                lock.lock();
                System.out.println("isLocked ? " + lock.isLocked());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };

        pool.submit(runnable);
        TimeUnit.MILLISECONDS.sleep(500);
        pool.submit(runnable);
        pool.awaitTermination(2, TimeUnit.SECONDS);
    }

    /**
     * {@link Condition} 可以达到 synchronized, wait() 和 notify()/notifyAll() 同样的功能，实现线程阻塞和唤醒
     * {@link Condition#await()} 实现线程阻塞，同时会让线程释放掉锁；
     * {@link Condition#signal()} 将会唤醒线程，唤醒前必须先持有锁。
     *
     * 一个 ReentrantLock 可以与多个 Condition 相关联，但各个 Condition 彼此独立
     *
     * 注意：使用 Condition 实例方法前必须先保持锁定
     */
    @Test
    public void condition() throws Exception {
        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Random random = new Random();

        Runnable runnable = () -> {
            int rand = random.nextInt(4);
            System.out.println(Thread.currentThread().getName() + " random number is " + rand);
            try {
                lock.lock();
                if (rand % 2 == 0) {
                    condition2.await();
                    System.out.println(Thread.currentThread().getName() + " condition2 await release");
                } else {
                    condition1.await();
                    System.out.println(Thread.currentThread().getName() + " condition1 await release");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };

        for (int i = 0; i < 5; i++) {
            pool.submit(runnable);
        }

        TimeUnit.SECONDS.sleep(1);

        try {
            lock.lock();
            System.out.println("condition2 will signalAll threads");
            // 唤醒所有被 condition2 阻塞的线程，condition1 将会继续被阻塞
            condition2.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * {@link ReentrantLock#getWaitQueueLength(Condition)} 返回等待与此锁定相关的给定Condition的线程数
     * 就是有多少个指定的Condition实例在等待此锁定
     */
    @Test
    public void getWaitQueueLength() throws Exception {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Runnable runnable = () -> {
            try {
                // 当前线程加锁
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " get lock!");
                TimeUnit.MILLISECONDS.sleep(100);
                // Condition#await() 方法会使当前线程进行等待，且会释放。这样其他线程就会持有锁了。当需要唤醒的时候，也需要先持有锁
                condition.await();
                System.out.println(Thread.currentThread().getName() + " after condition await");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };

        for (int i = 0; i < 5; i++) {
            pool.submit(runnable);
        }

        TimeUnit.SECONDS.sleep(1);

        try {
            // Condition#await() 让线程等待的时候会释放掉锁，所以想要通过 Condition#signal() 唤醒线程，需要先持有锁
            lock.lock();
            int waitQueueLength;
            // 可以通过 Condition#signalAll() 来唤醒所有被 condition 阻塞的线程
            while ((waitQueueLength = lock.getWaitQueueLength(condition)) > 0) {
                System.out.println("wait queue length is " + waitQueueLength);
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * {@link ReentrantLock#hasWaiters(Condition)} 方法是查询是否有线程正在等待与此锁定有关的Condition条件
     */
    @Test
    public void hasWaiters() throws Exception {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Runnable runnable = () -> {
            try {
                lock.lock();
                condition.await();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };

        for (int i = 1; i <= 5; i++) {
            pool.submit(runnable);
        }

        TimeUnit.SECONDS.sleep(1);
        try {
            // 调用 Condition 相关方法时，必须先持有锁，否则不在一个线程调用的时候，将会抛出 IllegalMonitorStateException 异常
            lock.lock();
            System.out.println(lock.hasWaiters(condition));
        } finally {
            lock.unlock();
        }
    }


    // @Test
    public void getWaitingThreads() {
        // ReentrantLock lock = new ReentrantLock();
        /**
         * {@link InterruptibleReentrantLock} 是自定义的继承自 {@link ReentrantLock} 的类
         * 将 {@link ReentrantLock#getWaitingThreads(Condition)} 暴露了出来以便测试使用
         */
        InterruptibleReentrantLock lock = new InterruptibleReentrantLock();
        Condition condition = lock.newCondition();
        for (int i = 0; i < 5; i++) {
            pool.execute(() -> {
                Thread currentThread = Thread.currentThread();
                PrintUtil.println("==============", currentThread.getName());
                lock.lock();
                try {
                    /**
                     * wait()/await() 方法推荐使用的方式如下：
                     * while (<condition does not hold>) {
                     *     condition.await()
                     * }
                     * 这里为了方便测试，暂时未使用该方式。
                     * 另外，condition.await()/condition.signal() 需要在获取到相关锁后才能调用，否则会抛出异常
                     */
                    condition.await();
                    Collection<Thread> waitingThreads = lock.pubGetWaitingThreads(condition);
                    if (waitingThreads.isEmpty()) {
                        PrintUtil.println("No thread is waiting in this condition!");
                    } else {
                        waitingThreads.forEach(thread -> PrintUtil.println("Waiting Thread name is ", thread.getName()));
                    }
                } catch (Exception e) {
                    PrintUtil.println(">>>>>>>>>>>>", currentThread.getName(), " has been interrupted!<<<<<<<<<<<");
                    currentThread.interrupt();
                } finally {
                    lock.unlock();
                }
            });
        }

    }

}
