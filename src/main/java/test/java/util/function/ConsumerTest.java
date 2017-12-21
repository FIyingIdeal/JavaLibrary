package test.java.util.function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * @author yanchao
 * @date 2017/12/21 13:47
 * @see BiConsumerTest
 * Consumer是一个functional interface，它有一个抽象的accept()方法
 * Consumer的使用场景是：执行接收“一个”参数但不返回结果的操作（如果要接收多个参数的话，使用BiConsumer），泛型用来约束参数的类型
 *  如：执行输出操作；调用接收参数但没有返回值的方法
 *
 * Consumer与Supplier之间的区别：
 *      Consumer接收参数但没有返回值
 *      Supplier不接受参数但有返回值
 */
public class ConsumerTest {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    class ConsumerInnerClass {
        public ConsumerInnerClass() {}
        public void add(int a) {
            int time = 3;
            int result = a * time;
            logger.info("{} * {} = {}", a, time, result);
        }
    }

    @Test
    public void print() {
        Consumer<String> consumer = s -> System.out.println(s);
        Consumer<String> simpleConsumer = System.out::println;
        consumer.accept("Hello Consumer");
        simpleConsumer.accept("Hello Consumer");
    }

    @Test
    public void invokeMethod() {
        Consumer<Integer> consumer = (a) -> new ConsumerInnerClass().add(a);
        consumer.accept(2);
    }

    @Test
    public void primitiveConsumer() {
        IntConsumer intConsumer = i -> System.out.println(i);
        intConsumer.accept(33);
    }
}
