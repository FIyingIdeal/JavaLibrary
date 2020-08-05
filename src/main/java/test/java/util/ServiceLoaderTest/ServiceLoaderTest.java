package test.java.util.ServiceLoaderTest;

import sun.reflect.Reflection;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author yanchao
 * @date 2018/1/29 16:34
 *
 * SPI机制，全称为Service Provider Interface，是JDK内置的一种服务提供发现机制。
 * 一个服务（Service）通常指的是已知的接口或抽象类，服务提供方就是对这个接口或抽象类的实现。
 * 然后按照SPI标准放到资源路径META-INF/services目录
 * （有IDE的问题，本测试用例的该目录是在target/classes中）下，文件的命名为该服务接口的全限定名
 */
public class ServiceLoaderTest {

    public static void main(String[] args) {
        System.out.println("ServiceLoader's classLoader is " + ServiceLoader.class.getClassLoader());
        System.out.println("Hello's classLoader is " + Hello.class.getClassLoader());
        ServiceLoader<Hello> serviceLoader = ServiceLoader.load(Hello.class);
        Iterator<Hello> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Hello hello = iterator.next();
            System.out.println(hello.sayHello("Java"));
        }

        Class<?> reflection = Reflection.getCallerClass(2);
        System.out.println(reflection);
    }
}
