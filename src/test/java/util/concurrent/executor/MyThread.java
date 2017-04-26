package test.java.util.concurrent.executor;

/**
 * Created by Administrator on 2017/2/28.
 */
public class MyThread extends Thread {

    @Override
    public void run() {
        //while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "正在执行！");
        //}
    }
}
