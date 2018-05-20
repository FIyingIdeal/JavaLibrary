package test.java.initTest;

/**
 * Created by Administrator on 2016/7/18.
 */
public class InitTest extends Parent {

    // 静态代码块在类加载的时候只执行一次
    static {
        System.out.println("InitTest static block");
    }

    // 动态代码块在每次构造对象的时候执行，且先于本类的构造函数执行，后于父类的构造器执行
    {
        System.out.println("InitTest dynamic block");
    }

    public InitTest() {
        // System.out.println("InitTest constructure block");
        this("");
    }

    // 构造函数在构造对象的时候执行，晚于动态代码块执行
    public InitTest(String name) {
        System.out.println("InitTest constructure block : " + name);
    }

    public void method() {
        System.out.println("InitTest Mothod");
    }


    public static void main(String[] args) {
        InitTest test1 = new InitTest();
        System.out.println("test1.s = " + test1.getS());
        InitTest test2 = new InitTest();
        System.out.println("test2.s = " + test2.getS());

    }
}

class Parent {

    private String s;

    static {
        System.out.println("Parent static block");
    }

    {
        System.out.println("Parent dynamic block: s = " + s);
    }

    // 父类构造方法先于子类的动态代码块执行
    public Parent() {
        this.s = "123";
        System.out.println("Parent Constructor");
    }

    public String getS() {
        return this.s;
    }

}
