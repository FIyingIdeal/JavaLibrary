package test.java.util.concurrent.exchanger;

import java.util.concurrent.Exchanger;

/**
 * Created by Administrator on 2017/3/2.
 * Exchange--交换机，可以在两个线程之间交换数据，只能是两个线程。
 * 当线程A调用Exchange对象的exchange()方法后，它会进入阻塞状态，直到线程B也调用了exchange()方法后，
 * 两者以线程安全的方式交换数据，之后两者继续运行。
 */
public class Client {
    public static void main(String[] args) {
        Exchanger exchanger = new Exchanger();
        Object object1 = "A";
        Object object2 = "B";
        Object object3 = "C";
        Object object4 = "D";

        new Thread(new ExchangerRunnable(exchanger, object1), "ThreadA").start();
        new Thread(new ExchangerRunnable(exchanger, object4), "ThreadB").start();
        new Thread(new ExchangerRunnable(exchanger, object3), "ThreadC").start();
        new Thread(new ExchangerRunnable(exchanger, object2), "ThreadD").start();
    }
}
