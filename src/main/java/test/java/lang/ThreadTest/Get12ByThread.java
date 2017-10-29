package test.java.lang.ThreadTest;

/**
 * Created by Administrator on 2016/7/15.
 * 使用两个线程交替打印出1和2，即出现效果为1212121212...，而不能有两个连续的1或2
 */
public class Get12ByThread extends Thread {

    private Object obj;
    private int num;

    public Get12ByThread(Object obj, int num) {
        this.obj = obj;
        this.num = num;
    }

    @Override
    public void run() {
        /*while (true) {
            synchronized (obj) {
                try {
                    obj.notifyAll();
                    obj.wait();
                    System.out.print(num);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/
        while (true) {
            synchronized (obj) {
                try {
                    System.out.print(num);
                    obj.notifyAll();
                    obj.wait(); //必须先notify其他的线程，再wait，不然的话每个线程都先wait后睡眠了，就无法继续执行notify()
                } catch (Exception e) {

                }
            }
        }
    }

    public static void main(String[] args) {
        Object obj = new Object();

        new Get12ByThread(obj, 1).start();
        new Get12ByThread(obj, 2).start();
    }
}
