package test.java.util.concurrent.countDownLatch;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/3/1.
 * CountDownLatch是一个并发构造，它允许一个或多个线程等待一系列执行操作的完成。
 * 初始化的时候指定一个初始量n，每调用一次countDown()就将n减1
 * 通过调用await()方法之一，线程可以阻塞  直到 n == 0
 */
public class Client {
    @Test
    public void test() {
        CountDownLatch latch = new CountDownLatch(3);
        new Thread(new Waiter(latch)).start();
        new Thread(new Decrementer(latch)).start();

        //让当前线程等待子线程执行完毕
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
