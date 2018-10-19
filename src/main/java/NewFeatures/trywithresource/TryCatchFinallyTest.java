package NewFeatures.trywithresource;

/**
 * @author yanchao
 * @date 2018/1/23 16:31
 */
public class TryCatchFinallyTest {

    public static boolean getBoolean1() {
        boolean b = true;
        try {
            throw new NullPointerException();
        } catch (NullPointerException e) {
            System.out.println("catch block");
            return !b;
        } finally {
            System.out.println("finally block");
        }
    }

    public static boolean getBoolean2() {
        boolean b = true;
        try {
            throw new NullPointerException();
        } catch (NullPointerException e) {
            System.out.println("catch block");
            throw e;
        } finally {
            System.out.println("finally block");
            // return和throw都是返回执行结果的方式，正常来说throw和return放一起是会报错的
            // 但是异常处理机制的finally是必须执行的，所以如果finally里面有return或者throw，那么catch的return或者throw就被覆盖
            return !b;
        }
    }

    public static void main(String[] args) {
        boolean b1;
        try {
            b1 = getBoolean1();
        } catch (NullPointerException e) {
            System.out.println("main catch block");
            throw e;
        }
        System.out.println(b1);
        // catch block
        // finally block
        // false

        System.out.println("---------------");

        boolean b2;
        try {
            b2 = getBoolean2();
        } catch (NullPointerException e) {
            System.out.println("main catch block");
            throw e;
        }
        System.out.println(b2);
        // catch block
        // finally block
        // false
    }
}
