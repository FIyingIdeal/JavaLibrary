package algorithm.ratelimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数器限流算法
 * @author yanchao
 * @date 2020-08-05 16:19
 */
public class RateCounter implements RateLimiter {

    private static final int DEFAULT_RATE_LIMIT_PER_SECOND = Integer.MAX_VALUE;

    private int limit;

    private AtomicInteger counter;

    public RateCounter() {
        this(DEFAULT_RATE_LIMIT_PER_SECOND);
    }

    public RateCounter(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit less then 0");
        }
        this.limit = limit;
        counter = new AtomicInteger();
        TimestampHolder holder = new TimestampHolder();
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(() -> {
            while (true) {
                long cur = System.currentTimeMillis();
                // 超过指定时间（这里设置的是 1s，即流量限制是 1s 多少请求），重置计数器
                if (cur - holder.getTimestamp() >= 1000) {
                    holder.setTimestamp(cur);
                    counter.set(0);
                }

                try {
                    // 每次休眠 1ms， emmm，就是等等，没什么特殊含义，防止太快~
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void acquire() {
        if (counter.incrementAndGet() > limit) {
            throw new RejectException();
        }
    }

    public static void main(String[] args) {
        RateLimiter rateLimiter = new RateCounter(10);
        int num = 100;

        while (num > 0) {
            try {
                // 如果指定时间段内申请不到计数器，则会抛出异常，捕获异常后接着重试
                // 重试前可以做一些处理，如进行当前请求的页面响应。申请到则正常处理业务逻辑
                rateLimiter.acquire();
            } catch (Exception e) {
                continue;
            }
            num--;
            System.out.println("sec: " + System.currentTimeMillis() / 1000L + ", mil: "
                    + System.currentTimeMillis() + " got a token");
        }
    }
}
