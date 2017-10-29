package test.java.initTest.JVM.passiveReference;

/**
 * Created by Administrator on 2017/7/17.
 */
public class SubClass extends SuperClass {
    static {
        System.out.println("sub class");
    }

    @Override
    protected void overrideTest() {

    }

}
