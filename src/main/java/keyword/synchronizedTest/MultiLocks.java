package keyword.synchronizedTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 同一个线程多个锁的测试
 * @author yanchao
 * @date 2020-08-02 10:32
 */
public class MultiLocks {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            es.submit(new LockTestTask(lock1, lock2));
        }

        es.awaitTermination(10, TimeUnit.SECONDS);
        es.shutdown();
    }

    private static class LockTestTask implements Runnable {

        private final Object lock1;
        private final Object lock2;

        LockTestTask(Object lock1, Object lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            System.out.println("Task will lock for thread : " + Thread.currentThread().getName());
            synchronized (lock1) {
                System.out.println("Task has been locked by lock1 for thread : " + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("Task has been locked by lock2 for thread : " + Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
