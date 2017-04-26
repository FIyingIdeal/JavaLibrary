package test.java.lang.ThreadTest.threeThread;

/**
 * Created by Administrator on 2017/3/21.
 * @see ThreeThreadTest 功能一致：使三个线程依次打印出A,B,C
 */
public class ThreeThread {

    public static void main(String[] args) {
        Object first = new Object();
        Object second = new Object();
        Object third = new Object();
        ThreeThreadInner threadA = new ThreeThreadInner(first, third, "A");
        ThreeThreadInner threadB = new ThreeThreadInner(second, first, "B");
        ThreeThreadInner threadC = new ThreeThreadInner(third, second, "C");
        try {
            threadA.start();
//            Thread.sleep(10);
            threadB.start();
            //Thread.sleep(10);
            threadC.start();
        } catch (Exception e) {}
    }
}

class ThreeThreadInner extends Thread {

    private Object head;
    private Object last;
    private String name;
    private int i = 0;

    public ThreeThreadInner(Object head, Object last, String name) {
        this.name = name;
        this.head = head;
        this.last = last;
    }

   @Override
    public void run() {
        try {
            while (i < 10) {
                synchronized (last) {
                    synchronized (head) {
                        System.out.print(this.name + i + " ");
                        head.notify();
                    }
                    last.wait();
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 先wait，后notify的情况：死锁，但由于线程调度的问题，执行结果不确定，
     * 假设线程A,B,C先后进入线程方法，线程A持有A,C锁，线程B会先持有B锁，等待A锁，线程C等待线程A释放C锁（获取C锁以后才能获取B锁），
     * 当A线程执行last.wait()后，释放了C锁，然后线程A睡眠了...睡眠了...等待notify()的唤醒，而此时它还没有释放掉A锁。
     * 此时线程C可以获取C锁以后等待B锁。But，线程A呼呼大睡不释放A锁，线程B等待A锁不释放B锁，线程C等待B锁...造成死锁
     */
    /*@Override
    public void run() {
        try {
            while (i < 10) {
                synchronized (head) {
                    synchronized (last) {
                        System.out.print(this.name + i + " ");
                        last.wait();
                    }
                    head.notify();
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}