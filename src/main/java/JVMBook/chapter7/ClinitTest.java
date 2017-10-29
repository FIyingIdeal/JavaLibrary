package JVMBook.chapter7;

/**
 * @author yanchao
 * @date 2017/10/12 20:18
 */
public class ClinitTest {

    static class Parent {

        public static int A = 1;

        static {
            //定义在静态代码块之前的类变量的赋值先于静态代码块的执行
            //因为 <clinit> 方法使用编译器自动收集类中的所有类变量的赋值动作和静态语句块中的语句合并而成，
            //编译器收集的顺序是由语句在源文件中出现的顺序决定的
            System.out.println("before static block A = " + A);
            A = 2;
        }
    }

    static class Sub extends Parent {
        public static int B = A;
    }

    public static void main(String[] args) {
        System.out.println(Sub.B);
    }
}
