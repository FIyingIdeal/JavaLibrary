package test.java.util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author yanchao
 * @date 2018/11/1 17:49
 */
public class TimerTest {

    static class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " execute!");
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Timer 运行是单线程的
        Timer timer = new Timer();
        for (int i = 0; i < 5; i++) {
            timer.schedule(new MyTimerTask(), 1);
        }

        try {
            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.schedule(new MyTimerTask(), 1);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Timer只有在 cancel() 后才停止，否则在执行完所有任务后并不会退出，而是阻塞等待任务
        timer.cancel();
    }
}
