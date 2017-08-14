package test.java.initTest.JVM.passiveReference;

/**
 * Created by Administrator on 2017/7/17.
 */
public class ConstClass {

    static {
        System.out.println("const class");
    }

    public static final int num = 123;
}
