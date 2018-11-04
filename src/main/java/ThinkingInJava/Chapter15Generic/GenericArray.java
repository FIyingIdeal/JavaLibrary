package ThinkingInJava.Chapter15Generic;

import java.lang.reflect.Array;

/**
 * @author yanchao
 * @date 2018/10/22 13:08
 */
public class GenericArray<E /*extends Integer*/> {

    private E[] e;

    public GenericArray(int size) {
        e = (E[]) new Object[size];
    }

    public static void main(String[] args) {
        // 成功创建一个泛型数组的唯一方式是创建一个类型擦除的数组，然后转型
        GenericArray<Integer>[] a = (GenericArray<Integer>[]) new GenericArray[10];
        // GenericArray[]
        System.out.println(a.getClass().getSimpleName());
        // 数组能追踪元素的实际类型，这个类型是在数组创建的时候建立的.
        // 无法将一个 Object[] 转换成 GenericArray[]
        // GenericArray<Integer>[] b = (GenericArray<Integer>[]) new Object[10];

        // 由于泛型擦除，构造方法里的类型转换相当于 (object[]) new Object[size]，所以可以执行成功
        // 假如将 E 的声明变为 <E extends Integer>，这里执行将会失败，因为泛型擦除是擦除到第一边界，即 Integer
        GenericArray genericArray = new GenericArray(10);
        // Object[]
        System.out.println(genericArray.e.getClass().getSimpleName());

        int[] intArray = (int[])Array.newInstance(Integer.TYPE, 10);

    }

    // 以 C 开头的为类名，以 I 开头的为接口名，以 G 开头的为泛型参数名
    class CA {}
    interface IC {}
    interface ID extends IC {}
    interface IF {}

    class CB implements IC, IF {

        public <GA extends IC> void method1(GA param) {}
        public <GB extends IF> void method2(GB param) {}

        public void test() {
            CB cb = new CB();
            method1(cb);
            method2(cb);
        }
    }

    // extends 后边可以有多个类型，类型之间使用 & 分隔，但必须先写类名后写接口名
    // 同时，由于Java单继承机制的限制，最多只能有一个类，但可以有多个接口，
    class CC<T extends CA & IC>{}
    // 注意 Test2中 T 的边界，必须至少满足 Test1 中对 T 边界的要求，即至少满足 T extends A & C
    class CD<T extends CA & ID> extends CC<T> {}
}
