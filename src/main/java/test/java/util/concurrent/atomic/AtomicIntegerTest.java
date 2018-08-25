package test.java.util.concurrent.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yanchao
 * @date 2018/8/6 18:24
 */
public class AtomicIntegerTest {

    @Test
    public void compareAndSet() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        boolean result = atomicInteger.compareAndSet(1, 2);
        System.out.println("result is " + result);

        // compareAndSet是直接修改内存的
        AtomicInteger integer1 = null;
        AtomicInteger integer2 = integer1;
        integer1 = new AtomicInteger(1);
        // integer1 = 1, integer2 = null
        System.out.println("integer1 = " + integer1 + ", integer2 = " + integer2);

        AtomicInteger integer3 = integer1;
        // integer1 = 1, integer3 = 1
        System.out.println("integer1 = " + integer1 + ", integer3 = " + integer3);
        integer1.compareAndSet(1, 3);
        // integer1 = 3, integer3 = 3
        System.out.println("integer1 = " + integer1 + ", integer3 = " + integer3);
    }
}
