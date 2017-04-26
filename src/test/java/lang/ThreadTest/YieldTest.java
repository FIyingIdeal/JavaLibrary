package test.java.lang.ThreadTest;

/**
 * Created by Administrator on 2016/7/26.
 *
 * yield()方法是让线程从执行状态进入可执行状态，本意是让当前线程让出CPU来执行其他线程，
 * 但实际中无法保证yield()达到让步目的，因为让步的线程还有可能被线程调度程序再次选中
 */
public class YieldTest extends Thread {

    private int i = 0;

    public YieldTest(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (i < 20){
            System.out.println(Thread.currentThread().getName() + " ==> " + i++);
            if (i % 3 == 0) {
                this.yield();
            }
        }
    }

    public static void main(String[] args) {
        YieldTest test1 = new YieldTest("Thread1");
        YieldTest test2 = new YieldTest("Thread2");

        test1.start();test2.start();
    }
}
