package test.java.util.concurrent.locks;

import org.junit.After;
import org.junit.Before;
import utils.PrintUtil;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yanchao
 * @date 2019/3/9 12:25
 */
public class ReentrantLockTest {

    private ExecutorService pool;

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

    public static void main(String[] args) {
        ReentrantLockTest test = new ReentrantLockTest();
        test.init();
        test.getWaintingThreads();
        test.close();
    }

    // @Test
    public void getWaintingThreads() {
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
