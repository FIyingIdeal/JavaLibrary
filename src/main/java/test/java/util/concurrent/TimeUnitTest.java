package test.java.util.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author yanchao
 * @date 2018-4-25 16:06:39
 */
public class TimeUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(TimeUnitTest.class);

    @Test
    public void test() {
        logger.info("1 seconds to minute : {}", TimeUnit.SECONDS.toMinutes(1L));
    }

    @Test
    public void convert() {
        long millis = 1000;
        long seconds = TimeUnit.SECONDS.convert(millis, TimeUnit.MILLISECONDS);
        logger.info("1000millis convert to {} seconds!", seconds);
}

    }