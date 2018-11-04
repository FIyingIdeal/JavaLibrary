package test.java.lang.reflect;

import java.lang.reflect.Field;

/**
 * @author yanchao
 * @date 2018/7/29 15:53
 * 通过反射方式可以修改使用final修饰的“常”量的值
 */
public class ReflectFinalTest {

    public static void main(String[] args) throws Exception {
        ReflectFinal reflectFinal = new ReflectFinal();
        Field field = ReflectFinal.class.getDeclaredField("i");
        field.setAccessible(true);
        // 0
        System.out.println("before setInt(), i = " + field.get(reflectFinal));
        field.setInt(reflectFinal, 9);
        // 9
        System.out.println("after setInt(), i = " + field.get(reflectFinal));

    }

}

class ReflectFinal {
    private final int i = 0;
}
