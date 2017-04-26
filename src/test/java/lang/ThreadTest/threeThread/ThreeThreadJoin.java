package test.java.lang.ThreadTest.threeThread;

/**
 * Created by Administrator on 2017/3/22.
 * 使用join让ABC三个线程依次启动：在一个线程中启动另一个线程，并执行该线程的join方法
 */
public class ThreeThreadJoin {

    public static void main(String[] args) {
        ThreeThreadJoinInner threadA = new ThreeThreadJoinInner("A");
        ThreeThreadJoinInner threadB = new ThreeThreadJoinInner(threadA, "B");
        ThreeThreadJoinInner threadC = new ThreeThreadJoinInner(threadB, "C");
        try {
            threadC.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class ThreeThreadJoinInner extends Thread {
    private Thread thread;
    private String name;

    public ThreeThreadJoinInner(String name) {
        this.name = name;
    }

    public ThreeThreadJoinInner(Thread thread, String name) {
        this.thread = thread;
        this.name = name;
    }

    @Override
    public void run() {
        if (thread == null) {
            System.out.print(name);
            return;
        }

        try {
            thread.start();
            thread.join();
            System.out.print(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
