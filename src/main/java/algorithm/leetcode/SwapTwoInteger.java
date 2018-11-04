package algorithm.leetcode;

import java.lang.reflect.Field;

/**
 * @author yanchao
 * @date 2018/7/27 18:28
 * https://blog.csdn.net/k1280000/article/details/71159492
 */
public class SwapTwoInteger {

    public static void main(String[] args) {
        Integer  a = 1, b = 2;
        System.out.println("a = " + a + ", b = " + b);
        swap(a, b);
        System.out.println("a = " + a + ", b = " + b);

        int i = Integer.valueOf(1);
        System.out.println("======i = " + i);
        i = Integer.valueOf(2);
        System.out.println("=====i = " + i);
    }

    public static void swap(Integer i1, Integer i2) {
        try {
            // 通过反射方式修改了对应的值，但也会造成一个严重的问题，即IntegerCache中1和2的位置颠倒了
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            // int temp = i1.intValue();
            Integer temp = new Integer(i1.intValue());
            field.set(i1, i2.intValue());
            field.set(i2, temp);

            int i = Integer.valueOf(1);
            System.out.println("i = " + i);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基本类型的变体引用类型和String都不能直接当纯引用类型来用，比如Integer,Double等都是int与double的引用类型，
     * 但是你不能像普通引用类型那样直接对他的值做改变，因为在他们里面封装的原始int与double都用了final进行声明。
     * 所以你就算重新赋值了原始的int与double都不会改变。
     * @param i1
     * @param i2
     */
    public static void swap1(Integer i1, Integer i2) {
        System.out.println("i1 = " + i1 + ", i2 = " + i2);
        i1 = i1 + i2;
        i2 = i1 - i2;
        i1 = i1 - i2;
        System.out.println("i1 = " + i1 + ", i2 = " + i2);
    }

    public static void swap0(Integer i1, Integer i2) {
        System.out.println("i1 = " + i1 + ", i2 = " + i2);
        Integer i3 = i1;
        i1 = i2;
        i2 = i3;
        System.out.println("i1 = " + i1 + ", i2 = " + i2);
    }
}
