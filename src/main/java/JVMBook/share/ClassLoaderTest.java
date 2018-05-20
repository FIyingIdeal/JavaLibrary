package JVMBook.share;

/**
 * @author yanchao
 * @date 2018/5/20 23:37
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        System.out.println(ClassLoader.class == ClassLoader.class);
        System.out.println(ClassLoader.class.equals(ClassLoader.class));
    }
}
