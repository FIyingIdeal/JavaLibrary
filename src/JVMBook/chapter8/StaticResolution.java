package JVMBook.chapter8;

/**
 * @author yanchao
 * @date 2017/10/17 14:52
 * 方法静态解析
 * 使用 javap -v 反编译生成的class文件，可以看到在调用sayHello()的地方使用的是invokestatic指令
 */
public class StaticResolution {

    public static void sayHello() {
        System.out.println("Hello");
    }

    public static void main(String[] args) {
        StaticResolution.sayHello();
    }
}
