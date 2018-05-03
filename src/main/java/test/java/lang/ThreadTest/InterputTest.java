package test.java.lang.ThreadTest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yanchao
 * @date 2018/5/1 13:04
 * 当在线程上调用interrupt()时，中断发生的唯一时刻是在任务要进入到阻塞操作中或已经在阻塞操作内部时。
 *      这意味着如果线程在循环中碰巧没有产生任何阻塞调用的情况下，如果在循环条件中没有使用Thread.interrupted()来判断线程时候被中断，那么即使使用
 *      interrupt()对线程进行了中断操作也是无效的。
 * 使用Thread.interrupted()可以检查中断状态，它不仅可以判断interrupt()方法是否被调用过，还可以清除中断状态。
 */
public class InterputTest {

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                int i = 0;
                try {
                    while (i++ < 30) {
                    // while (i++ < 30 && !Thread.interrupted()) {
                        System.out.println(i + Thread.currentThread().getName() + " is running!");
                        if (i == 2) {
                            Thread.currentThread().interrupt();
                            long x = System.currentTimeMillis();
                            System.out.println("x = " + x);
                            if (x % 2 == 0) {
                                Thread.sleep(1000);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
