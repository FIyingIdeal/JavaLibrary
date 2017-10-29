package JVMBook.chapter8;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author yanchao
 * @date 2017/10/18 9:50
 */
public class MethodHandleTest {

    static class ClassA {
        public void println(String s) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj = new ClassA();
        getPrintlnMH(obj).invokeExact("Hello MethodHandle!");
    }

    private static MethodHandle getPrintlnMH(Object receiver) throws Throwable {
        MethodType methodType = MethodType.methodType(void.class, String.class);
        return MethodHandles.lookup().findVirtual(receiver.getClass(), "println", methodType).bindTo(receiver);
    }
}
