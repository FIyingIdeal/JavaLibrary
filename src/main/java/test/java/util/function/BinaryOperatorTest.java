package test.java.util.function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * @author yanchao
 * @date 2017/12/21 16:43
 * @see FunctionTest
 * @see BiFunctionTest
 */
public class BinaryOperatorTest {

    private static final Logger logger = LoggerFactory.getLogger(BinaryOperatorTest.class);

    /**
     * BinaryOperator是一个特殊的BiFunction，同样是三个泛型参数，但三个泛型参数的类型是一致的
     */
    @Test
    public void testBinaryOperatorApply() {
        BinaryOperator<String> stringConcat = (s1, s2) -> s1.concat(s2);
        BinaryOperator<String> simpleStringConcat = String::concat;
        logger.info("After concat two word is {}", stringConcat.apply("hello", " world"));
        logger.info("After concat two word is {}", simpleStringConcat.apply("hello", " world"));
    }

    @Test
    public void testBinaryOperatorAndThen() {
        Function<String, Integer> countLength = String::length;
        BinaryOperator<String> stringConcat = String::concat;
        int afterConcatLength = stringConcat.andThen(countLength).apply("hello", "world");
        logger.info("length is  {}", afterConcatLength);
    }
}
