package test.java.util.function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.UnaryOperator;

/**
 * @author yanchao
 * @date 2017/12/29 10:17
 * UnaryOperator继承自Function接口，是一个特殊的Function，它的接收参数类型和返回参数类型是一致的
 */
public class UnaryOperatorTest {

    private static final Logger logger = LoggerFactory.getLogger(UnaryOperatorTest.class);

    @Test
    public void apply() {
        UnaryOperator<String> unaryOperator = str -> str.toUpperCase();
        UnaryOperator<String> simpleUnaryOperator = String::toUpperCase;
        logger.info("UnaryOperator --> {}", unaryOperator.apply("hello world"));
        logger.info("Method Reference UnaryOperator --> {}", simpleUnaryOperator.apply("hello world"));
    }

    @Test
    public void identity() {
        UnaryOperator<String> identity = UnaryOperator.identity();
        identity = String::toUpperCase;
        logger.info("UnaryOperator identity --> {}", identity.apply("hello"));
    }
}
