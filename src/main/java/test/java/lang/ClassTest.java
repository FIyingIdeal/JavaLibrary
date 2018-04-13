package test.java.lang;

import org.junit.Test;

/**
 * @author yanchao
 * @date 2018/4/13 14:32
 */
public class ClassTest {

    @Test
    public void getName() {
        Class<TestInterface> clazz = TestInterface.class;
        System.out.println(clazz.getName());      // test.java.lang.TestInterface
    }

    @Test
    public void toStringTest() {
        Class<TestInterface> clazz = TestInterface.class;
        System.out.println(clazz.toString());   // interface test.java.lang.TestInterface
    }

}

interface TestInterface {

    String getName();
}
