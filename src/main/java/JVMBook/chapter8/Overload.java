package JVMBook.chapter8;

/**
 * @author yanchao
 * @date 2018/11/7 11:16
 */
public class Overload {

    /*public static void sayHello(Object arg) {
        System.out.println("hello Object");
    }*/

    public static void sayHello(char arg) {
        System.out.println("hello char");
    }

    public static void sayHello(Character arg) {
        System.out.println("hello Character");
    }

    // 无法与 char... arg共存
    /*public static void sayHello(Character... arg) {
        System.out.println("hello Character...");
    }*/

    /*public static void sayHello(Object... arg) {
        System.out.println("hello Object...");
    }*/

    public static void sayHello(int... arg) {
        System.out.println("hello int...");
    }

    public static void sayHello(char... arg) {
        System.out.println("hello char...");
    }

    public static void main(String[] args) {
        sayHello('a');
    }
}
