package test.java.util.function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.DoubleToIntFunction;

/**
 * @author yanchao
 * @date 2017/12/22 9:31
 */
public class DoubleToIntFunctionTest {

    private static final Logger logger = LoggerFactory.getLogger(DoubleToIntFunctionTest.class);

    @Test
    public void test() {
        DoubleToIntFunction function = d -> (int) d;
        double dNumber = 3.1415926;
        logger.info("double to int result : {} => {}", dNumber, function.applyAsInt(dNumber));
    }
}
