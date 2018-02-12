package test.java.lang.ClassLoaderTest;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author yanchao
 * @date 2018/1/25 9:55
 * @reference http://swiftlet.net/archives/868
 */
public class ClassLoaderTest {

    /**
     * @reference http://swiftlet.net/archives/868
     * @function Class.getResource与ClassLoader.getResource的区别
     *
     * Class.getResource(String path)
     *      参数path不以'/'开头时，默认是从此类所在的包下获取资源
     *      而path以'/'开头时，则是从项目的Classpath根下获取资源，这里的'/'表示Classpath
     *
     * Class.getClassLoader().getResource(String path)
     *      参数path不以'/'开头时，path是指类加载器的加载范围，在资源加载的过程中，使用的逐级向上委托的形式加载；
     *      而path以'/'开头时，'/'表示Boot ClassLoader中的加载范围，因为这个类加载器是C++实现的，所以加载范围为null
     *
     * 从运行结果中可以看出来，Class.getResource("/") 与 Class.getClassLoader().getResource("")是一样的，
     * 其实Class.getResource与ClassLoader.getResource本质上是一样的，都是使用ClassLoader.getResource来加载资源
     * 而在{@link Class#getResource(String)}中使用{@link Class#resolveName(String)}方法对参数进行了处理，
     * 如果是'/'开头的话会去掉'/'，因此两者表现是一样的。如果不是'/'开头的话，会将包路径拼接上
     *
     * 注意：getResource(String) 只返回第一个加载到的文件，即使存在多个文件
     *
     * @see Class#getResource(String)
     * @see Class#resolveName(String)
     */
    @Test
    public void getResource() {

        URL classUrl = this.getClass().getResource("test.txt");
        System.out.println("classUlr '' : " + classUrl);
        // classUlr '' : file:/E:/IDEAWorkspace/Java8/target/classes/test/java/lang/ClassLoaderTest/
        classUrl = this.getClass().getResource("/");
        System.out.println("classUlr / : " + classUrl);
        // classUlr / : file:/E:/IDEAWorkspace/Java8/target/classes/

        URL classLoaderUrl = this.getClass().getClassLoader().getResource("");
        System.out.println("classLoaderUrl '' : " + classLoaderUrl);
        // classLoaderUrl '' : file:/E:/IDEAWorkspace/Java8/target/classes/
        classLoaderUrl = ClassLoaderTest.class.getClassLoader().getResource("/");
        System.out.println("classLoaderUrl / : " + classLoaderUrl);
        // classLoaderUrl / : null
    }

    /**
     * 其加载文件路径规则同getResource()一致，参考上边的描述
     * 由于IDE编译完的class文件是在target目录下，所以test.txt是在该目录的对应的文件夹中，而不是在src/java对应的目录中
     */
    @Test
    public void getResourceAsStream() {
        // Class.getResourceAsStream(String)  相对当前路径
        InputStream classResourceStream = this.getClass().getResourceAsStream("test.txt");
        if (classResourceStream == null) {
            System.out.println("Class.getResourceAsStream(String) failed!");
            return ;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(classResourceStream));
        reader.lines().forEach(System.out::println);

        // Class.getResourceAsStream(String)  绝对路径
        InputStream classResourceStream_absolute = this.getClass().getResourceAsStream("/test/java/lang/ClassLoaderTest/test.txt");
        if (classResourceStream_absolute == null) {
            System.out.println("Class.getResourceAsStream(String) absolute failed!");
            return ;
        }
        BufferedReader reader_absolute = new BufferedReader(new InputStreamReader(classResourceStream_absolute));
        reader_absolute.lines().forEach(System.out::println);

        // ClassLoader.getResourceAsStream(String) 查看源码会发现其实使用的就是ClassLoader#getResource(String)，这个方法返回的是一个URL对象，然后调用其openStream()方法就得到了InputStream
        InputStream classLoaderResourceStream = this.getClass().getClassLoader()
                .getResourceAsStream("test/java/lang/ClassLoaderTest/test.txt");
        if (classLoaderResourceStream == null) {
            System.out.println("ClassLoader.getResourceAsStream(String) failed!");
            return ;
        }
        BufferedReader classLoaderReader = new BufferedReader(new InputStreamReader(classLoaderResourceStream));
        classLoaderReader.lines().forEach(System.out::println);
    }

    /**
     * ClassLoader.getSystemResourceAsStream(String name) 其本质也是调用了{@link ClassLoader#getResource(String)}
     * 会先查找是否存在SystemClassLoader(即AppClassLoader)，如果存在的话会直接调用其getResource(String)方法
     * 否则的话委派为Bootstrap ClassLoader进行查找
     * 由于其调用的还是{@link ClassLoader#getResource(String)}，所以文件路径规则同其一致
     * @throws IOException
     */
    @Test
    public void getSystemResourceAsStream() throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream("test/java/lang/ClassLoaderTest/test.txt");
        if (is == null) {
            System.out.println("File not found!");
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String str;
        while ((str = reader.readLine()) != null) {
            System.out.println(str);
        }
    }
}
