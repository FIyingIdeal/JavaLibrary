package test.java.initTest.staticInitTest;

/**
 * Created by Administrator on 2017/3/1.
 */
public class StaticMethodClass {
    //直接调用类的静态方法也会引起类的加载，并执行静态代码块，但不会引起动态代码块的执行
    static {
        System.out.println("静态代码块执行");
    }

    {
        System.out.println("动态代码块执行");
    }

    public StaticMethodClass() {
        System.out.println("构造方法执行");
    }

    public static void staticMethod() {
        System.out.println("静态方法执行");
    }
}
