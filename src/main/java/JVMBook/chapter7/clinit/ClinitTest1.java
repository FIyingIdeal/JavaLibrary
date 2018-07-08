package JVMBook.chapter7.clinit;

/**
 * @author yanchao
 * @date 2017/11/1 11:40
 */
public class ClinitTest1 {

    static int a = 2;

    static {
        System.out.println("a = " + a);     //a = 2
        a = 5;
        b = 4;
    }

    static int b = 3;

    public static void main(String[] args) {
        System.out.println("a = " + a);     //a = 5
        System.out.println("b = " + b);     //b = 3
    }
}
