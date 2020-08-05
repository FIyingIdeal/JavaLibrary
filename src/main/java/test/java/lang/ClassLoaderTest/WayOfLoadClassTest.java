package test.java.lang.ClassLoaderTest;

/**
 * @author yanchao
 * @date 2020-07-24 15:29
 */
public class WayOfLoadClassTest {

    public static int i = 123;

    static {
        System.out.println("WayOfLoadClassTest 类被加载");
    }

    {
        i = 789;
        System.out.println("WayOfLoadClassTest 动态代码块被执行");
    }
}
