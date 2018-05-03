package test.java.util.concurrent.threadfactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author yanchao
 * @date 2018/5/1 12:36
 */
public class MyThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("In UncaughtExceptionHandler! " + t.getName() + " " + e.getClass() + ":" + e.getMessage());
            }
        });
        return thread;
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool(new MyThreadFactory());
        service.execute(() -> {
            try {
                throw new NullPointerException("在run()方法中捕获异常后，UncaughtExceptionHandler()中无法再次捕获到该异常");
            } catch (NullPointerException e) {
                System.out.println("In run() catch : " + e.getMessage());
            }
            throw new RuntimeException("在run()方法中抛出的未捕获RuntimeException");
        });
        service.shutdown();
    }
}
