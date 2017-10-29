package test.java.lang.IntegerTest;

import org.junit.Test;

/**
 * Created by Administrator on 2017/3/10.
 */
public class IntegerTest {
    @Test
    public void equalTest() {
        //Integer内部有一个缓存池，用来存储 [-128,127] 之间的整数，如果超出则新建对象
        Integer i1 = 127;
        Integer i2 = 127;
        System.out.println("Integer(127) == Integer(127) ? " + (i1 == i2));//true

        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println("Integer(128) == Integer(128) ? " + (i3 == i4));//false

        //new出来两个对象，肯定不相等
        Integer i5 = new Integer(127);
        Integer i6 = new Integer(127);
        System.out.println("new Integer(127) == new Integer(127) ? " + (i5 == i6));//false
    }
}
