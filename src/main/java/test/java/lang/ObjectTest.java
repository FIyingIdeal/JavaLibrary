package test.java.lang;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author yanchao
 * @date 2018/4/25 10:07
 */
public class ObjectTest {

    private static final Logger logger = LoggerFactory.getLogger(ObjectTest.class);

    private class InnerClass {
        private String name;
        private int age;
        private float test;

        public InnerClass(String name, int age, float test) {
            this.name = name;
            this.age = age;
            this.test = test;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            InnerClass that = (InnerClass) o;
            return age == that.age &&
                    Float.compare(that.test, test) == 0 &&
                    Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, test);
        }
    }

    @Test
    public void equalsTest() {
        logger.info("null instanceof String : {}", null instanceof String);
    }

    @Test
    public void hashCodeTest() {
        InnerClass innerClass = new InnerClass("zhangsan", 20, 20.1f);
        // 同一个对象的hashCode一定相同
        Assert.assertEquals(innerClass.hashCode(), innerClass.hashCode());
        InnerClass innerClass1 = new InnerClass("zhangsan", 20, 20.1f);
        // 重写了equals()方法，比较InnerClass中的name属性一致，则两个对象就equals : true
        logger.info("Objects.equals(innerClass, innerClass1) : {}", Objects.equals(innerClass, innerClass1));
        // 如果只是重写了equals()方法，而没有重写hashCode()方法的话，即使两个InnerClass对象中的name属性equals，但他们的hashCode不用
        // 这与hashCode通用约定违背：如果两个对象根据equals(Object)方法比较是相等的，那么在两个对象上调用hashCode就必须产生的结果是相同的整数
        // 所以重写了equals()方法必须重写hashCode()方法，否则与HashMap，HashSet无法同用
        logger.info("Objects.equals(innerClass.hashCode(), innerClass1.hashCode()) = {}",
                Objects.equals(innerClass.hashCode(), innerClass1.hashCode()) );
    }
}
