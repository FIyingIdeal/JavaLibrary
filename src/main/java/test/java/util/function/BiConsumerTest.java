package test.java.util.function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.*;

/**
 * @author yanchao
 * @date 2017/12/21 14:45
 * BiConsumer与Consumer {@link ConsumerTest}类似，接收参数但没有返回值
 * 但两者不同的是Consumer只能接收一个参数，而BiConsumer可以接收两个参数
 */
public class BiConsumerTest {

    private static final Logger logger = LoggerFactory.getLogger(BiConsumerTest.class);

    class BiConsumerInnerClass {
        public BiConsumerInnerClass() {}

        public void add(int a, int b) {
            int sum = a + b;
            logger.info("{} + {} = {}", a, b, sum);
        }
    }

    @Test
    public void test() {
        BiConsumer<Integer, Integer> biConsumer = (a, b) -> new BiConsumerInnerClass().add(a, b);
        biConsumer.accept(2,3);
    }

    @Test
    public void invoker() {
        Supplier<BiConsumerInnerClass> supplier = BiConsumerInnerClass::new;
        supplierMethod(supplier);
    }


    /**
     * 该方式只是进行尝试将Supplier当做方法参数进行测试
     * @param supplier
     */
    public void supplierMethod(Supplier<BiConsumerInnerClass> supplier) {
        BiConsumer<Integer, Integer> consumer = (a, b) -> supplier.get().add(a, b);
        consumer.accept(3,4);
    }

    @Test
    public void biConsumerWithMap() {
        Map<String, Integer> map = new HashMap<>();
        BiConsumer<String, Integer> biConsumer = (k, v) -> map.put(k ,v);
        BiConsumer<String, Integer> biConsumer1 = map::put;
        biConsumer.accept("first", 1);
        biConsumer1.accept("second", 2);
        BiFunction<Map<String, Integer>, String, Integer> biFunction = (m, s) -> m.get(s);
        logger.info("{} = {}", "first", biFunction.apply(map, "first"));
        logger.info("{} = {}", "second", biFunction.apply(map, "second"));
    }
}
