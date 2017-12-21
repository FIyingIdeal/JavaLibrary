package test.java.util.function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author yanchao
 * @date 2017/12/21 17:16
 * Predicate是一个Functional Interface，它包含一个抽象的test(T)方法
 * Predicate用来检查一个给定的条件表达式的真假，并返回true或false。
 * 它与Function类似，都是接收一个参数并返回一个结果，但不同的是Function返回的是一个Object，而Predicate返回的是一个Boolean
 */
public class PredicateTest {

    private static final Logger logger = LoggerFactory.getLogger(PredicateTest.class);

    @Test
    public void test() {
        Predicate<Integer> predicate = a -> a > 10;
        logger.info("result : {}", predicate.test(3));
    }

    @Test
    public void and() {
        Predicate<Integer> predicate1 = a -> a > 10;
        Predicate<Integer> predicate2 = a -> a > 20;
        Predicate<Integer> predicate3 = predicate1.and(predicate2);
        logger.info("result1 : {}", predicate3.test(14));
        logger.info("result2 : {}", predicate3.test(24));
    }

    @Test
    public void predicateReferenceTest() {
        List<String> names = Arrays.asList("zhangsan", "lisi", "yanzi");
        Predicate<String> predicate = name -> name.startsWith("y");
        predicateReference(names, predicate);

        //////使用Stream实现上边的功能//////
        names.stream().filter(predicate).forEach(name -> logger.info("【Use Stream】 {} is starting with 'y'", name));
    }

    /**
     * 这里使用Consumer只是为了测试和熟悉方法，实际中可以使用Stream更加简便
     * @param names
     * @param predicate
     */
    public void predicateReference(List<String> names, Predicate<String> predicate) {
        Consumer<String> consumer = name -> {
            if (predicate.test(name)) {
                logger.info("【Unuse Stream】{} is starting with 'y'", name);
            }
        };
        for (String name : names) {
            consumer.accept(name);
        }
    }

    @Test
    public void isEqual() {
        logger.info("result : {}", Predicate.isEqual("string").test("string1"));
    }
}
