package guava.common.util.concurrent;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yanchao
 * @date 2018/10/16 9:51
 */
public class RateLimiterTest {

    private static final Logger logger = LoggerFactory.getLogger(RateLimiterTest.class);

    @Test
    public void create() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // 每秒钟颁发一个令牌
        RateLimiter rateLimiter = RateLimiter.create(0.5);
        for (int i = 0; i < 20; i++) {
            double waitTime = rateLimiter.acquire();
            executorService.execute(() -> {
                logger.info("{} waits {}", Thread.currentThread().getName(), waitTime);
            });
        }
    }
}
