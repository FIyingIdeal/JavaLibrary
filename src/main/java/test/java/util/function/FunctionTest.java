package test.java.util.function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * @author yanchao
 * @date 2017/12/21 15:44
 * @see BiFunctionTest
 * @see BinaryOperatorTest
 * Function是一个Functional Interface {@link NewFeatures.FunctionalInterface}，它拥有一个抽象的apply(T)方法
 * Function使用的场景：执行接收一个参数并拥有返回值的操作
 */
public class FunctionTest {

    private static final Logger logger = LoggerFactory.getLogger(FunctionTest.class);

    @Test
    public void testFunctionApply() {
        Function<String, Integer> function = s -> s.length();
        Function<String, Integer> function1 = String::length;
        logger.info("function's length is {}", function.apply("function"));
        logger.info("function1's length is {}", function1.apply("function1"));
    }
}
