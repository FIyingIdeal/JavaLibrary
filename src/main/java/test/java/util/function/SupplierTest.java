package test.java.util.function;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * @author yanchao
 * @date 2017/12/21 13:58
 * Supplier是一个functional interface，它有一个抽象的get()方法
 * Supplier的使用场景：执行一个不接收参数但有返回值的操作，泛型用来约束返回值的类型
 *      如：
 *          1.使用无参构造方法构造一个实例；
 *          2.调用无参有返回值的方法
 */
public class SupplierTest {

    private static final Logger logger = LoggerFactory.getLogger(SupplierTest.class);

    class InnerClass {

        private String name;

        public InnerClass() {
            this.name = "default";
        }

        public InnerClass(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "InnerClass{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void newInstance() {
        Supplier<InnerClass> defaultInstance = InnerClass::new;
        Supplier<InnerClass> nameInstance = () -> new InnerClass("Supplier");
        logger.info("default instance : {}", defaultInstance.get());
        logger.info("name instance : {}", nameInstance.get());

    }

    @Test
    public void test2() {
        Supplier<LocalDateTime> supplier = LocalDateTime::now;
        logger.info("now is {}", supplier.get());
    }

    @Test
    public void primitiveSupplier() {
        IntSupplier intSupplier = new Random(47)::nextInt;
        IntSupplier intSupplier1 = () -> 23;
        logger.info("IntSupplier : {}", intSupplier.getAsInt());
        logger.info("IntSupplier : {}", intSupplier1.getAsInt());

    }
}
