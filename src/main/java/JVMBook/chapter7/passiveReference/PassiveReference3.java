package JVMBook.chapter7.passiveReference;

/**
 * @author yanchao
 * @date 2018/5/31 11:00
 * 编译期常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化
 */
public class PassiveReference3 {

    public static void main(String[] args) {
        // 被动引用编译期常量测试，不会触发类的初始化
        System.out.println(Parent.FINAL_STR);
        // 运行期常量测试，会触发类的初始化（将Parent中的RUNTIME_FINAL_STR注释去掉）
        System.out.println(Parent.RUNTIME_FINAL_STR);
        // 主动引用测试：当访问类的静态字段时（执行getstatic字节码，前提示该静态字段未被final修饰）会触发类的初始化
        // System.out.println(Parent.STR);

    }
}
