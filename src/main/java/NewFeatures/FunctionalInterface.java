package NewFeatures;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yanchao
 * @date 2017/12/21 16:04
 * @reference http://www.topjavatutorial.com/java-8/functional-interface-in-java8/
 * Functional Interface 是JDK8提供的新特性，其定义与正常的interface的定义类似，但只能有一个abstract方法，可以有一个或多个static和default方法
 * Functional Interface 的主要优点是可以用来分配一个lambda表达式，或者可以用做方法引用
 * 其中lambda表达式相当与了该Interface中的那个abstract方法的一个实现，指定完毕以后就可以直接调用方法进行调用了
 */
public class FunctionalInterface {

    private static final Logger logger = LoggerFactory.getLogger(FunctionalInterface.class);

    /**
     * 定义的一个Functional Interface @FunctionInterface 注解用来标识一个借口是Functional Interface，但非必须的。建议使用该注解标识。
     */
    @java.lang.FunctionalInterface
    interface MyFunctionInterface {
        int add(int a, int b);
    }

    @Test
    public void functionInterfaceTest() {
        //为Functional Interface的实例分配一个lambda表达式，相当于对Functional Interface中abstract方法的实现，然后就可以执行这个方法了
        MyFunctionInterface myFunctionInterface = (a, b) -> a + b;
        int sum = myFunctionInterface.add(2, 3);
        logger.info("sum is {}", sum);
    }
}
