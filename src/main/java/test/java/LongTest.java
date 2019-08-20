package test.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author yanchao
 * @date 2019-08-19 18:39
 */
@Slf4j
public class LongTest {

    /**
     * 通过 {@link Long#compare(long, long)} 来比较两个 long 类型的数
     * 该方法需要两个参数，分别为 (x, y)，返回值如下 ：
     *      x > y     =>  1
     *      x == y    =>  0
     *      x < y     =>  -1
     */
    @Test
    public void compare() {
        long l1 = 23L;
        long l2 = 34L;
        log.info("Long.compare({}, {}) => {}", l1, l2, Long.compare(l1, l2));
    }
}
