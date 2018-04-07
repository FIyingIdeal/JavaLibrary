package test.java.lang.ClassLoaderTest;

import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader {

    public static void main(String[] args) throws Exception {
        ClassLoader myLoader =  new ClassLoader() {
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
    }

}
