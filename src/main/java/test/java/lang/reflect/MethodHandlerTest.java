package test.java.lang.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlerTest {

    static class MyOutput {

        public void println(String obj) {
            System.out.println("MyOutput : " + obj);
        }
    }

    public static MethodHandle getMethodHandle(Object object) throws Exception {
        // MethodType表示一个方法的类型，其参数为 返回值类型，参数类型（还有其他的重载形式）
        // 这里找到一个对应类型的方法表示，通过lookup来找到方法对应的MethodHandle
        MethodType methodType = MethodType.methodType(void.class, String.class);
        return MethodHandles.lookup().findVirtual(object.getClass(), "println", methodType).bindTo(object);
    }

    public static void main(String[] args) throws Throwable {

        Object object = null;

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                object = System.out;
            } else {
                object = new MyOutput();
            }
            MethodHandle methodHandle = getMethodHandle(object);
            methodHandle.invokeExact("test");
        }


    }


}
