package test.java.initTest.JVM.passiveReference;

/**
 * Created by Administrator on 2017/7/17.
 */
public class SuperClass {

    static {
        System.out.println("super class");
    }

    protected void overrideTest() {
        System.out.println("Super protected method");
    }

    public static int num = 9;
}
