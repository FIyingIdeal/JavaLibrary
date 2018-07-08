package JVMBook.share;

/**
 * @author yanchao
 * @date 2018/5/23 22:56
 * 使用 javap -v 反编译class文件可以看到常量池的内容
 */
public class ConstantPool {

    public static String s1 = "static";
    public static void main(String[] args) {
        String s2 = new String("he") + "llo";
        String s3 = "hello";
    }
}
