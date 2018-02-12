package test.java.lang.ClassLoaderTest;

import org.junit.Test;

/**
 * Created by Administrator on 2017/7/13.
 */

/**
 * {@code AppClassLoader} OR {@code SystemClassLoader}
 * 负责在JVM启动时，加载来自命令java中的-classpath或者java.class.path系统属性或者CLASSPATH操作系统属所指定的JAR类包和类路径。
 * 调用 {@code ClassLoader.getSystemClassLoader()} 可以获取该类加载器。
 * 如果没有特别指定，则用户自定义的任何类加载器都将该类加载器作为它的父加载器，详情见{@link java.lang.ClassLoader#ClassLoader()}
 */
public class AppClassLoader {

    @Test
    public void test() {
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(System.getProperty("CLASSPATH"));
    }

    /**
     * 获取{@code AppClassLoader}的父加载器ExtClassLoader
     */
    @Test
    public void getParent() {
        System.out.println(
                ClassLoader.getSystemClassLoader().getParent());
        //输出结构为 ： sun.misc.Launcher$ExtClassLoader@31befd9f
    }
}
