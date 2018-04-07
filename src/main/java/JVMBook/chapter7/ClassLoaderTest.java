package JVMBook.chapter7;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yanchao
 * @date 2017/10/13 11:12
 *
 * 对于任意一个类，都需要由加载它的类加载器和这个类本身一同确立其在Java虚拟机中的唯一性，每一个类加载器，都拥有一个独立的类名称空间
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {

        // 该ClassLoader直接重写了loadClass(String)方法，破坏了双亲委派模型，不会调用父类ClassLoader(自定义ClassLoader的父ClassLoader是AppClassLoader)
        // 来加载指定的类，而是使用自定义的ClassLoader进行类加载的，对象obj就是这个类加载器加载的ClassLoaderTest生成的对象，
        // 但instanceof后边的ClassLoaderTest则是由AppClassLoader加载的，所以这两个同名的类是被不同的ClassLoader加载的，也就是说这两个类“不相等”，因此构造一个类构造出来的对象并不属于另一个类的对象
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String filename = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(filename);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myLoader.loadClass("JVMBook.chapter7.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof JVMBook.chapter7.ClassLoaderTest);        //false

        // 该ClassLoader实现了findClass(String)方法，findClass(String)方法是在loadClass(String)进行完双亲委派查找之后，
        // 仍然没有找到对应的类的时候才会调用，因此即使在这里定义了该ClassLoader，但其实依然使用的是AppClassLoader加载的，所以结果为true
        ClassLoader myFindClassLoader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                String filename = name.substring(name.lastIndexOf(".") + 1) + ".class";
                try (InputStream is = getClass().getResourceAsStream(filename)) {
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj1 = myFindClassLoader.loadClass("JVMBook.chapter7.ClassLoaderTest").newInstance();
        System.out.println(obj1.getClass());
        System.out.println(obj1 instanceof JVMBook.chapter7.ClassLoaderTest);      //true
    }
}
