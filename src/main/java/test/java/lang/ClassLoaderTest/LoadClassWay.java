package test.java.lang.ClassLoaderTest;

/**
 * 加载类的几种方式：
 *      1. JVM 初始化时加载；
 *      2. 通过 {@link Class#forName(String)} 及其重载方法加载；
 *      3. 通过 {@link ClassLoader#loadClass(String)} 加载，但这样会破坏双亲委派模型；
 *
 * 通过 {@link Class#forName(String)} 与 {@link ClassLoader#loadClass(String)} 加载类是有区别的，
 *      主要体现在是否执行静态代码块（准备阶段），具体看下边测试。
 *
 *
 * @author yanchao
 * @date 2020-07-24 15:17
 */
public class LoadClassWay {

    /**
     * 静态内部类不会在外部类加载时被加载，两者没有任何关系
     */
    /*public static class WayOfLoadClassTest {

        static {
            System.out.println("静态内部类被加载");
        }
    }*/

    static {
        System.out.println("LoadClassWay 被加载");
    }

    public static void main(String[] args) throws Exception {
        String name = "test.java.lang.ClassLoaderTest.WayOfLoadClassTest";

        // 1. 通过 ClassLoader#loadClass 加载，加载后并没有执行静态代码块，即没有执行加载类的准备阶段
        // loadClassByClassLoader(name);

        // 2. 通过 Class#forName(String) 加载，执行了被加载类的静态代码块，即执行了加载类的准备阶段
        // loadClassByClassForName(name);

        // 3. 通过 Class#forName(String, boolean, ClassLoader) 加载类，并指定第二个类为false，则不会执行被加载类的静态代码块
        // loadClassByClassForName(name, false, LoadClassWay.class.getClassLoader());

        // 4. 在 3 的基础上进行测试，在引用类变量的时候会执行类的静态代码块
        loadClassByClassForNameAndNewInstance(name);
    }

    private static void loadClassByClassLoader(String name) throws ClassNotFoundException {
        // 通过 ClassLoader#loadClass 加载
        System.out.println("通过 ClassLoader#loadClass 加载其他类");
        ClassLoader classLoader = LoadClassWay.class.getClassLoader();
        System.out.println("current classloader is " + classLoader);
        // 加载后并没有执行静态代码块，及没有执行加载类的准备阶段
        classLoader.loadClass(name);
    }

    private static void loadClassByClassForName(String name) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(name);
        System.out.println(clazz);
    }

    private static void loadClassByClassForName(String name, boolean initialize, ClassLoader classLoader)
            throws ClassNotFoundException {
        Class<?> clazz = Class.forName(name, initialize, classLoader);
        System.out.println(clazz);
    }

    private static void loadClassByClassForNameAndNewInstance(String name)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(name, false, LoadClassWay.class.getClassLoader());
        System.out.println(clazz);
        System.out.println(WayOfLoadClassTest.i);
        /*WayOfLoadClassTest wayOfLoadClassTest = (WayOfLoadClassTest) clazz.newInstance();
        System.out.println(WayOfLoadClassTest.i);*/
    }
}
