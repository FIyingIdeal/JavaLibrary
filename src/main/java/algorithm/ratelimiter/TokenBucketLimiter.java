package algorithm.ratelimiter;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author yanchao
 * @date 2020-08-05 17:23
 */
public class TokenBucketLimiter implements RateLimiter {

    private static final int DEFAULT_RATE_LIMIT_PER_SECOND = Integer.MAX_VALUE;

    private static final long NANOSECOND = 1000 * 1000 * 1000;

    private static final Object TOKEN = new Object();

    private BlockingQueue<Object> tokenBucket;

    public TokenBucketLimiter() {
        this(DEFAULT_RATE_LIMIT_PER_SECOND);
    }

    public TokenBucketLimiter(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit lass than 0");
        }
        tokenBucket = new LinkedBlockingQueue<>(limit);

        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        TimestampHolder holder = new TimestampHolder(System.nanoTime());
        // NANOSECOND 是 1s，limit 表示 1s 内的最大请求量，interval 表示生成令牌的时间间隔
        long interval = NANOSECOND / limit;

        threadPool.submit(() -> {
            while (true) {
                long cur = System.nanoTime();
                if (cur - holder.getTimestamp() >= interval) {
                    // 使用put 在桶满了以后会阻塞
                    this.tokenBucket.put(TOKEN);
                    holder.setTimestamp(cur);
                }

                try {
                    TimeUnit.NANOSECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void acquire() throws RejectException {
        Object token = this.tokenBucket.poll();
        if (Objects.isNull(token)) {
            throw new RejectException();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = new TokenBucketLimiter(10);

        Runnable runnable = () -> {
            int num = 10;
            while (num > 0) {
                try {
                    rateLimiter.acquire();
                } catch (RejectException e) {
                    continue;
                }

                num--;
                System.out.println("Thread: " + Thread.currentThread().getName() + ", sec: "
                        + System.currentTimeMillis() / 1000L + ", mil: " + System.currentTimeMillis() + " got a token");
            }
        };

        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            threadPool.submit(runnable);
        }
        threadPool.awaitTermination(10, TimeUnit.SECONDS);
    }
}
