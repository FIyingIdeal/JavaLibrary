package JVMBook.chapter8;

/**
 * @author yanchao
 * @date 2017/10/17 15:53
 * 方法静态分派
 */
public class StaticDispatch {

    static abstract class Human {}

    static class Man extends Human {}

    static class Woman extends Human {}

    public void sayHello(Human human) {
        System.out.println("Hello, guy!");
    }

    public void sayHello(Man man) {
        System.out.println("Hello, gentleman!");
    }

    public void sayHello(Woman woman) {
        System.out.println("Hello, lady!");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch dispatch = new StaticDispatch();
        dispatch.sayHello(man);             //Hello, guy!
        dispatch.sayHello(woman);           //Hello, guy!

        Man real_man = new Man();
        Woman real_woman = new Woman();
        dispatch.sayHello(real_man);        //Hello, gentleman!
        dispatch.sayHello(real_woman);      //Hello, lady!
    }
}
