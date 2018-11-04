package test.java.util.concurrent.future;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author yanchao
 * @date 2018/9/5 19:23
 */
public class FutureTaskTest {

    /**
     * 自定义的FutureTask
     */
    class MyFutureTask extends FutureTask<String> {

        public MyFutureTask(Callable<String> callable) {
            super(callable);
        }

        public MyFutureTask(Runnable runnable, String result) {
            super(runnable, result);
        }

        @Override
        protected void done() {
            System.out.println("FutureTask#done() executed");
        }
    }

    /**
     * 声明一个 MyFutureTask 实例变量
     */
    private FutureTask<String> futureTask = new MyFutureTask(() -> {
        TimeUnit.SECONDS.sleep(2);
        return "Task has already done!";
    });

    /**
     * {@link FutureTask#done()} 方法在线程任务执行完毕以后会被调用
     * @throws InterruptedException     {@link InterruptedException}
     * @throws ExecutionException       {@link ExecutionException}
     */
    @Test
    public void done() throws InterruptedException, ExecutionException {
        new Thread(futureTask).start();
        String result = futureTask.get();
        System.out.println("Callable result is : " + result);
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void oneThreadUseFutureTask() {
        Runnable runnable = () -> {
            System.out.println("task start");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task finished");
        };

        FutureTask futureTask = new FutureTask(runnable, "RESULT");
        futureTask.run();
        try {
            Object result = futureTask.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }



}
