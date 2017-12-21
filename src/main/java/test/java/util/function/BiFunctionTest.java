package test.java.util.function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiFunction;

/**
 * @author yanchao
 * @date 2017/12/21 10:51
 * @reference http://www.topjavatutorial.com/java-8/java-8-bifunction-functional-interface/
 * @see FunctionTest
 * @see BinaryOperatorTest
 * BiFunction与Function {@link FunctionTest}类似，都是执行接收参数并拥有返回值的操作
 * 但不用的是Function只能接收一个参数，而BiFunction可以接收两个参数（两个参数类型可以相同也可以不同）
 */
public class BiFunctionTest {

    private static final Logger logger = LoggerFactory.getLogger(BiFunctionTest.class);

    /**
     * BiFunction接收三个泛型参数的表达式定义，其中前两个是定义输入参数类型，最后一个是返回参数类型
     */
    @Test
    public void testBiFunctionApply() {
        BiFunction<String, String, Integer> lengthCountFunction = (s1, s2) -> s1.length() + s2.length();
        logger.info("Two String's length count is {}", lengthCountFunction.apply("hello", "world"));
    }
}
