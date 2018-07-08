package JVMBook.chapter7.passiveReference;

/**
 * @author yanchao
 * @date 2018/4/29 11:26
 */
public class Parent {

    public static String STR = "STATIC STRING";

    public final static String FINAL_STR = "FINAL STATIC STRING";

    // 常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此通过Parent.STR1方式调用该常量并不会触发定义常量类的初始化
    public static final String RUNTIME_FINAL_STR = "runtime final string".toLowerCase();

    static {
        System.out.println("Parent static block");
    }
}
