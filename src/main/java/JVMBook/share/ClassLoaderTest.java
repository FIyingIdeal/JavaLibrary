package JVMBook.share;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yanchao
 * @date 2018/5/20 23:37
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        System.out.println(ClassLoaderTest.class == ClassLoaderTest.class);
        System.out.println(ClassLoaderTest.class.equals(ClassLoaderTest.class));
        ClassLoader loader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String filename = name.substring(name.lastIndexOf(".") + 1) + ".class";
                try (InputStream is = getClass().getResourceAsStream(filename);) {
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException("IOException", e);
                }
            }
        };
        Class clazz = loader.loadClass("JVMBook.share.ClassLoaderTest");
        System.out.println(clazz == ClassLoaderTest.class);
    }
}
