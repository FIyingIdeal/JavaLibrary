package test.java.lang.ClassLoaderTest;

import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
/**
 * 参考： http://ifeve.com/classloader%E8%A7%A3%E6%83%91/
 * {@code BootstrapClassLoader} 称为引导类加载器，又称启动类加载器，是最顶层的类加载器，主要用来加载
 * Java核心类，如rt.jar, resource.jar, charsets.jar等。
 * 它不是{@link java.lang.ClassLoader}的子类，而是由JVM自身实现的该类C语言实现，Java程序无法访问到该加载器
 */
public class BootstrapClassLoaderTest {

    /**
     * 获取BootstrapClassLoader加载的jar包
     */
    @Test
    public void test() {
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].toExternalForm());
        }
    }
}
/**
 * 结果：
 *      file:/C:/Program%20Files/Java/jdk1.8.0_66/jre/lib/resources.jar
 *      file:/C:/Program%20Files/Java/jdk1.8.0_66/jre/lib/rt.jar
 *      file:/C:/Program%20Files/Java/jdk1.8.0_66/jre/lib/sunrsasign.jar
 *      file:/C:/Program%20Files/Java/jdk1.8.0_66/jre/lib/jsse.jar
 *      file:/C:/Program%20Files/Java/jdk1.8.0_66/jre/lib/jce.jar
 *      file:/C:/Program%20Files/Java/jdk1.8.0_66/jre/lib/charsets.jar
 *      file:/C:/Program%20Files/Java/jdk1.8.0_66/jre/lib/jfr.jar
 *      file:/C:/Program%20Files/Java/jdk1.8.0_66/jre/classes
 */
