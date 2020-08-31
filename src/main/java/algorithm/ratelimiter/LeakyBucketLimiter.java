package algorithm.ratelimiter;

import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * 漏桶限流算法
 * @author yanchao
 * @date 2020-08-05 16:46
 */
public class LeakyBucketLimiter implements RateLimiter {

    private static final int DEFAULT_RATE_LIMIT_PRE_SECONDS = Integer.MAX_VALUE;

    private static final long NANOSECOND = 1000 * 1000 * 1000;

    private BlockingQueue<Thread> bucket;

    public LeakyBucketLimiter() {
        this(DEFAULT_RATE_LIMIT_PRE_SECONDS);
    }

    public LeakyBucketLimiter(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("less than 0");
        }

        bucket = new LinkedBlockingQueue<>(limit);

        ExecutorService service = Executors.newSingleThreadExecutor();
        TimestampHolder holder = new TimestampHolder(System.nanoTime());
        // 每隔多长时间获取一个请求
        long interval = NANOSECOND / limit;
        service.submit(() -> {
            while (true) {
                long cur = System.nanoTime();
                // 控制漏桶流程速率
                if (cur - holder.getTimestamp() >= interval) {
                    // 从漏桶里获取一个线程执行。但也有可能漏桶是空的~
                    Thread thread = bucket.poll();
                    Optional.ofNullable(thread).ifPresent(LockSupport::unpark);
                    holder.setTimestamp(cur);
                }

                try {
                    TimeUnit.NANOSECONDS.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void acquire() throws RejectException {
        // 如果没有空间存放新来的请求，即超过漏铜容量，抛出异常
        if (bucket.remainingCapacity() == 0) {
            throw new RejectException();
        }
        Thread thread = Thread.currentThread();
        bucket.offer(thread);
        LockSupport.park();
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = new LeakyBucketLimiter(1);

        Runnable runnable = () -> {
            int num = 100;
            while (num > 0) {
                try {
                    rateLimiter.acquire();
                } catch (Exception e) {
                    continue;
                }
                num--;
                System.out.println("Thread: " + Thread.currentThread().getName() + ", sec: " + System.currentTimeMillis() / 1000L + ", mil: " + System.currentTimeMillis() + " got a token");
            }
        };

        long start = System.currentTimeMillis();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            threadPool.submit(runnable);
        }
        threadPool.awaitTermination(100, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        System.out.println("over time: " + (end - start) / 1000);
    }
}
