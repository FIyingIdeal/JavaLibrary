package test.java.initTest;

/**
 * Created by Administrator on 2016/7/18.
 */
public class InitTest1 {
    static {
        System.out.println("InitTest1 static block");
    }

    {
        System.out.println("InitTest1 dynamic block");
    }

    public static void main(String[] args) {
        InitTest test = new InitTest();     //会加载InitTest类，所以InitTest类的动态代码块也会执行
    }
}
