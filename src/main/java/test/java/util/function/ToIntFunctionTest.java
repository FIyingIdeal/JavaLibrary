package test.java.util.function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.ToIntFunction;

/**
 * @author yanchao
 * @date 2017/12/29 15:00
 * ToIntFunction也是一个特殊的Function，与IntFunction相反，它接收的是一个类型不确定的值，返回的是一个int类型的值
 */
public class ToIntFunctionTest {

    private static final Logger logger = LoggerFactory.getLogger(ToIntFunctionTest.class);

    @Test
    public void applyAsInt() {
        ToIntFunction<String> toIntFunction = Integer::parseInt;
        int i = toIntFunction.applyAsInt("20");
        logger.info("ToIntFunction --> {}", i);
    }
}
