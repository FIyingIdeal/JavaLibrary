package test.java.lang.ThreadTest;

/**
 * Created by Administrator on 2016/7/26.
 *
 * join()方法是指主线程等待子线程执行完成后再执行
 */
public class JoinTest extends Thread {

    private int i = 10;

    public JoinTest(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (i > 0) {
            System.out.println(Thread.currentThread().getName() + " ====> " + i--);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JoinTest jointest1 = new JoinTest("Thread1");
        JoinTest jointest2 = new JoinTest("Thread2");

        jointest1.start();
        try {
            jointest1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jointest2.start();

    }
}
