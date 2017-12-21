package test.java.util.function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiPredicate;

/**
 * @author yanchao
 * @date 2017/12/21 19:49
 * @see PredicateTest
 * BiPredicate与Predicate类似，用于判断一个表达式的真假
 * 不同的是BiPredicate接受两个参数，Predicate接接收一个参数
 */
public class BiPredicateTest {

    private static final Logger logger = LoggerFactory.getLogger(BiPredicateTest.class);

    @Test
    public void test() {
        BiPredicate<String, String> biPredicate = (s1, s2) -> s1.startsWith(s2);
        BiPredicate<String, String> simpleBiPredicate = String::startsWith;
        logger.info("result 1: {}", biPredicate.test("hellowoeld", "hello"));
        logger.info("result 2: {}", simpleBiPredicate.test("hellowoeld", "hello"));

        BiPredicate<Integer, Integer> p2 = (i1, i2) -> i1 > i2;
        logger.info("result 3: {}", p2.test(23, 45));
    }
}
