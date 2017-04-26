package test.java.lang.ThreadTest.threeThread;

/**
 * Created by Administrator on 2016/7/15.
 * 使用三个线程打印出 ABC 10次
 */
public class ThreeThreadTest extends Thread {

    private Object pre;
    private Object self;

    private int i = 0;

    private char c;

    public ThreeThreadTest(char c, Object pre, Object self) {
        this.pre = pre;
        this.self = self;
        this.c = c;
    }

    @Override
    public void run() {
        while (i < 10) {
                try {
                    synchronized (pre) {
                        synchronized (self) {
                            System.out.print(c);
                            self.notifyAll();
                        }
                        pre.wait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
        }
    }

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        try {
            new ThreeThreadTest('A', c, a).start();
            Thread.sleep(10);
            new ThreeThreadTest('B', a, b).start();
            Thread.sleep(10);
            new ThreeThreadTest('C', b, c).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
