package test.java.lang.ClassLoaderTest;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义 ClassLoader 实现类加载
 * @author yanchao
 * @date   2018-04-08
 */
public class MyClassLoader {

    public static void main(String[] args) throws Exception {
        ClassLoader myLoader =  new ClassLoader() {
            /**
             * 推荐重写 {@link ClassLoader#findClass(String)} 方法，而不是 {@link ClassLoader#loadClass(String)} 方法
             * 因为 双亲委派 就是在 {@link ClassLoader#loadClass(String)} 方法中实现的，只有在无法通过父 ClassLoader 加载类
             * 的时候才会去调用自定义 {@link ClassLoader#findClass(String)} 方法去加载类。
             * 如果自己重写了 {@link ClassLoader#loadClass(String)} 方法，就会破坏双亲委派模型
             */
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                String filename = name.substring(name.lastIndexOf(".") + 1) + ".class";
                try (InputStream is = getClass().getResourceAsStream(filename)) {
                    if (is == null) {
                        return super.findClass(name);
                    }
                    byte[] bytes = new byte[is.available()];
                    is.read(bytes);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object obj = myLoader.loadClass("test.java.lang.ClassLoaderTest.MyClassLoader").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof test.java.lang.ClassLoaderTest.MyClassLoader);

        /*ClassLoader myLoadClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf('.') + 1) + ".class";
                try (InputStream is = getClass().getResourceAsStream(fileName)) {
                    if (is == null) {
                        return super.loadClass(fileName);
                    }
                    byte[] bytes = new byte[is.available()];
                    is.read(bytes);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name, e);
                }
            }
        };
        Object obj1 = myLoadClassLoader.loadClass("test.java.lang.ClassLoaderTest.MyClassLoader").newInstance();
        System.out.println(obj1.getClass());
        System.out.println(obj1 instanceof test.java.lang.ClassLoaderTest.MyClassLoader);*/

    }

}
