package test.java.initTest;

/**
 * Created by Administrator on 2016/7/18.
 */
public class InitTest {

    //静态代码块在类加载的时候只执行一次
    static {
        System.out.println("static block");
    }

    //动态代码块在每次构造对象的时候执行，且先于构造函数执行
    {
        System.out.println("dynamic block");
    }

    //构造函数在构造对象的时候执行，晚于动态代码块执行
    public InitTest() {
        System.out.println("constructure block");
    }


    public static void main(String[] args) {
        InitTest test1 = new InitTest();

        InitTest test2 = new InitTest();

    }
}
