package test.java.lang.ClassLoaderTest;

import org.junit.Test;

/**
 * Created by Administrator on 2017/7/13.
 */

/**
 * {@code ExtClassLoader} 称为扩展类加载器，主要负责加载Java的扩展类库
 * 默认加载${JAVA_HOME}/jre/lib/ext/目录下的jar包或java.ext.dirs系统属性指定的jar包。
 * 放在这个目录下的jar包对所有{@code AppClassLoader}都是可见的（因为{@code ExtClassLoader}是{@code AppClassLoader}的父加载器）
 *
 * 一般认为{@code ExtClassLoader} 的父类加载器是 {@code BootstrapClassLoader}，但实际上它们根本没有父子关系
 * 只是在{@code ExtClassLoader}找不到要加载类的时候会去委托{@code BootstrapClassLoader}去加载
 */
public class ExtClassLoader {

    @Test
    public void test() {
        System.out.println(System.getProperty("java.ext.dirs"));
    }

    @Test
    public void getExtClassLoaderParent() {
        System.out.println(
                ClassLoader.getSystemClassLoader()      //得到SystemClassLoader/AppClassLoader
                    .getParent()                        //得到SystemClassLoader的父类加载器ExtClassLoader
                    .getParent()                        //获取ExtClassLoader的父类加载器，输出null
        );
    }
}