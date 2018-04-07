package test.java.initTest.staticInitTest;

/**
 * Created by Administrator on 2017/3/1.
 */
public class Main {
    private int i = 0;

    {
        i = 1;
    }

    public Main() {
        i = 2;
    }

    public static void main(String[] args) {
        Main m = new Main();
        System.out.println(m.i);
        StaticMethodClass.staticMethod();
    }
}
