package test.java.util;

import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author yanchao
 * @date 2018/11/13 16:37
 */
public class TreeMapTest {

    class A implements Comparable {
        @Override
        public int compareTo(Object o) {
            if (this.hashCode() > o.hashCode()) {
                return 1;
            } else if (this.hashCode() == o.hashCode()) {
                return 0;
            } else
                return -1;
        }
    }

    @Test
    public void put() {
        A a = new A();
        // TreeMap 的 key 值必须是一个实现了 Comparable 接口的类对象或向 TreeMap 中“注册”一个 Comparator
        Map<A, String> map = new TreeMap<>();
        map.put(a, "test");
        System.out.println(map.get(a));
    }
}
