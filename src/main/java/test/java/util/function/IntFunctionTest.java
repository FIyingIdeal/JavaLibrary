package test.java.util.function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.IntFunction;

/**
 * @author yanchao
 * @date 2017/12/29 14:55
 * IntFunction是一个特殊的Function，它的入参是一个int类型值，返回值不确定
 */
public class IntFunctionTest {

    private static final Logger logger = LoggerFactory.getLogger(IntFunction.class);

    @Test
    public void apply() {
        IntFunction<String> intFunction = String::valueOf;
        String s = intFunction.apply(20);
        logger.info("IntFunction --> {}", s);
    }
}
