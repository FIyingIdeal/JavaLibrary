package JVMBook.chapter8;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author yanchao
 * @date 2017/10/18 14:25
 * 掌握方法分派规则可以做到什么以前无法做到的事情
 */
public class InvokeDynamic {

    class GrandFather {
        void thinking() {
            System.out.println("I am grandfather");
        }
    }

    class Father extends GrandFather {
        void thinking() {
            System.out.println("I am father");
        }
    }

    class Son extends Father {
        void thinking() {
            //调用Father#thinking()方法十分简单，直接通过super就可
            //super.thinking();
            //只在该方法体内进行修改，且不直接new一个GrandFather的实例，调用到GrandFather#thinking()方法
            try {
                MethodType methodType = MethodType.methodType(void.class);
                MethodHandle methodHandle = MethodHandles.lookup().findSpecial(
                        GrandFather.class, "thinking", methodType, getClass());
                methodHandle.invoke(this);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        (new InvokeDynamic().new Son()).thinking();
    }
}


